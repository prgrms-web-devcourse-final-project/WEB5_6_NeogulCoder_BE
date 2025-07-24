package grep.neogulcoder.domain.review.service;

import grep.neogulcoder.domain.buddy.service.BuddyEnergyService;
import grep.neogulcoder.domain.review.*;
import grep.neogulcoder.domain.review.controller.dto.response.MyReviewTagsInfo;
import grep.neogulcoder.domain.review.controller.dto.response.ReviewContentsPagingInfo;
import grep.neogulcoder.domain.review.controller.dto.response.ReviewTargetStudiesInfo;
import grep.neogulcoder.domain.review.controller.dto.response.ReviewTargetUsersInfo;
import grep.neogulcoder.domain.review.entity.MyReviewTagEntity;
import grep.neogulcoder.domain.review.entity.ReviewEntity;
import grep.neogulcoder.domain.review.entity.ReviewTagEntity;
import grep.neogulcoder.domain.review.repository.MyReviewTagRepository;
import grep.neogulcoder.domain.review.repository.ReviewQueryRepository;
import grep.neogulcoder.domain.review.repository.ReviewRepository;
import grep.neogulcoder.domain.review.repository.ReviewTagRepository;
import grep.neogulcoder.domain.review.service.request.ReviewSaveServiceRequest;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.repository.StudyMemberQueryRepository;
import grep.neogulcoder.domain.study.repository.StudyMemberRepository;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.domain.users.entity.User;
import grep.neogulcoder.domain.users.repository.UserRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
import grep.neogulcoder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static grep.neogulcoder.domain.review.ReviewErrorCode.ALREADY_REVIEW_WRITE_USER;
import static grep.neogulcoder.domain.review.ReviewErrorCode.STUDY_NOT_FOUND;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReviewService {

    private final UserRepository userRepository;
    private final StudyRepository studyRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final StudyMemberQueryRepository studyMemberQueryRepository;

    private final ReviewTagFinder reviewTagFinder;
    private final ReviewRepository reviewRepository;
    private final ReviewQueryRepository reviewQueryRepository;
    private final MyReviewTagRepository myReviewTagRepository;
    private final ReviewTagRepository reviewTagRepository;

    private final BuddyEnergyService buddyEnergyService;

    public ReviewTargetUsersInfo getReviewTargetUsersInfo(long studyId, long userId) {
        List<StudyMember> studyMembers = studyMemberRepository.findFetchStudyByStudyId(studyId);

        List<ReviewEntity> reviews = reviewQueryRepository.findReviewsByReviewerInStudy(studyId, userId);
        List<User> users = findNotReviewedUsers(reviews, studyMembers);
        List<User> otherUsers = excludeMe(users, userId);

        return ReviewTargetUsersInfo.of(otherUsers);
    }

    public ReviewTargetStudiesInfo getReviewTargetStudiesInfo(long userId, LocalDateTime currentDateTime) {
        List<StudyMember> studyMembers = studyMemberQueryRepository.findMembersByUserId(userId);
        List<Study> studies = extractConnectedStudies(studyMembers);
        List<Study> reviewableStudies = filteredReviewableStudies(currentDateTime, studies);

        List<Long> studyIds = reviewableStudies.stream()
                .map(Study::getId)
                .toList();

        List<StudyMember> studyMemberList = studyMemberQueryRepository.findByIdIn(studyIds);


        Map<Long, Long> groupedStudyIdCountMap = studyMemberList.stream()
                .map(StudyMember::getStudy)
                .collect(
                        Collectors.groupingBy(
                                Study::getId,
                                Collectors.counting()
                        )
                );

        List<ReviewEntity> myReviews = reviewQueryRepository.findReviewsByReviewerInStudies(studyIds, userId);
        Map<Long, List<ReviewEntity>> groupedStudyIdMap = myReviews.stream()
                .collect(
                        Collectors.groupingBy(
                                ReviewEntity::getStudyId
                        )
                );

        List<Study> studies1 = studies.stream()
                .filter(study -> groupedStudyIdCountMap.get(study.getId()) != groupedStudyIdMap.get(study.getId()).size())
                .toList();

        return ReviewTargetStudiesInfo.of(studies1);
    }

    @Transactional
    public long save(ReviewSaveServiceRequest request, long writeUserId) {
        if (isAlreadyWrittenReviewBy(request.getStudyId(), request.getTargetUserId(), writeUserId)) {
            throw new BusinessException(ALREADY_REVIEW_WRITE_USER);
        }

        Study study = findValidStudy(request.getStudyId());
        ReviewTags reviewTags = ReviewTags.from(convertStringToReviewTag(request.getReviewTag()));
        ReviewType reviewType = reviewTags.ensureSingleReviewType();

        Review review = request.toReview(reviewTags.getReviewTags(), reviewType, writeUserId);
        List<ReviewTagEntity> reviewTagEntities = mapToReviewTagEntities(reviewTags);

        buddyEnergyService.updateEnergyByReview(request.getTargetUserId(), reviewType);

        return reviewRepository.save(ReviewEntity.from(review, reviewTagEntities, study.getId())).getId();
    }

    public MyReviewTagsInfo getMyReviewTags(long userId) {
        List<ReviewEntity> reviews = reviewRepository.findByTargetUserId(userId);
        List<ReviewTag> reviewTagList = extractReviewTags(reviews);

        ReviewTags reviewTags = ReviewTags.from(reviewTagList);
        Map<ReviewType, Map<ReviewTag, Integer>> tagCountMapByType = reviewTags.countTagsGroupedByReviewType();
        return MyReviewTagsInfo.of(tagCountMapByType);
    }

    public ReviewContentsPagingInfo getMyReviewContents(Pageable pageable, long userId) {
        Page<ReviewEntity> reviewPages = reviewQueryRepository.findContentsPagingBy(pageable, userId);
        List<ReviewEntity> reviews = reviewPages.getContent();

        List<User> users = findReviewWritersBy(reviews);
        Map<Long, User> userIdMap = mapUsersBy(users);
        return ReviewContentsPagingInfo.of(reviewPages, userIdMap);
    }

    private List<User> findNotReviewedUsers(List<ReviewEntity> reviews, List<StudyMember> studyMembers) {
        List<Long> reviewedUserIds = reviews.stream()
                .map(ReviewEntity::getTargetUserId)
                .toList();

        List<StudyMember> notReviewedStudyMembers = studyMembers.stream()
                .filter(studyMember -> !reviewedUserIds.contains(studyMember.getUserId()))
                .toList();

        List<Long> notReviewedUserIds = notReviewedStudyMembers.stream()
                .map(StudyMember::getUserId)
                .toList();

        return userRepository.findByIdIn(notReviewedUserIds);
    }

    private List<User> excludeMe(List<User> users, long userId) {
        return users.stream()
                .filter(user -> user.getId() != userId)
                .toList();
    }

    private List<Study> filteredReviewableStudies(LocalDateTime currentDateTime, List<Study> studies) {
        return studies.stream()
                .filter(study -> study.isReviewableAt(currentDateTime))
                .toList();
    }

    private List<Study> extractConnectedStudies(List<StudyMember> studyMembers) {
        return studyMembers.stream()
                .map(StudyMember::getStudy)
                .toList();
    }

    private Study findValidStudy(long studyId) {
        return studyRepository.findById(studyId)
                .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND));
    }

    private boolean isAlreadyWrittenReviewBy(long studyId, long targetUserId, long writeUserId) {
        return reviewQueryRepository.findBy(studyId, targetUserId, writeUserId) != null;
    }

    private List<ReviewTag> convertStringToReviewTag(List<String> reviewTags) {
        return reviewTags.stream()
                .map(reviewTagFinder::findBy)
                .toList();
    }

    private List<ReviewTagEntity> mapToReviewTagEntities(ReviewTags reviewTags) {
        List<String> reviewTagDescriptions = reviewTags.extractDescription();
        return reviewTagRepository.findByReviewTagIn(reviewTagDescriptions);
    }

    private List<ReviewTag> extractReviewTags(List<ReviewEntity> myReviews) {
        List<ReviewTagEntity> reviewTagEntities = getReviewTagEntities(myReviews);
        return reviewTagEntities.stream()
                .map(ReviewTagEntity::getReviewTag)
                .map(reviewTagFinder::findBy)
                .toList();
    }

    private List<ReviewTagEntity> getReviewTagEntities(List<ReviewEntity> myReviews) {
        List<Long> reviewIds = myReviews.stream()
                .map(ReviewEntity::getId)
                .toList();

        List<MyReviewTagEntity> myReviewTags = myReviewTagRepository.findByReviewTagIdFetchTag(reviewIds);
        return myReviewTags.stream()
                .map(MyReviewTagEntity::getReviewTag)
                .toList();
    }

    private List<User> findReviewWritersBy(List<ReviewEntity> reviews) {
        List<Long> writeUserIds = extractWriteUserIds(reviews);
        return userRepository.findByIdIn(writeUserIds);
    }

    private List<Long> extractWriteUserIds(List<ReviewEntity> reviews) {
        return reviews.stream()
                .map(ReviewEntity::getWriteUserId)
                .toList();
    }

    private Map<Long, User> mapUsersBy(List<User> users) {
        return users.stream()
                .collect(Collectors.toMap(
                        User::getId,
                        Function.identity()
                ));
    }
}
