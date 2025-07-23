package grep.neogulcoder.domain.review.service;

import grep.neogulcoder.domain.buddy.service.BuddyEnergyService;
import grep.neogulcoder.domain.review.*;
import grep.neogulcoder.domain.review.controller.dto.response.JoinedStudiesInfo;
import grep.neogulcoder.domain.review.controller.dto.response.MyReviewTagsInfo;
import grep.neogulcoder.domain.review.controller.dto.response.ReviewContentsPagingInfo;
import grep.neogulcoder.domain.review.controller.dto.response.ReviewTargetUsersInfo;
import grep.neogulcoder.domain.review.service.request.ReviewSaveServiceRequest;
import grep.neogulcoder.domain.review.entity.MyReviewTagEntity;
import grep.neogulcoder.domain.review.entity.ReviewEntity;
import grep.neogulcoder.domain.review.entity.ReviewTagEntity;
import grep.neogulcoder.domain.review.repository.MyReviewTagRepository;
import grep.neogulcoder.domain.review.repository.ReviewQueryRepository;
import grep.neogulcoder.domain.review.repository.ReviewRepository;
import grep.neogulcoder.domain.review.repository.ReviewTagRepository;
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

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static grep.neogulcoder.domain.review.ReviewErrorCode.*;

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

    public ReviewTargetUsersInfo getReviewTargetUsersInfo(long studyId, String myNickname) {
        List<StudyMember> studyMembers = findValidStudyMember(studyId);
        findValidStudy(studyId);

        List<User> targetUsers = findReviewTargetUsers(studyMembers, myNickname);
        return ReviewTargetUsersInfo.of(targetUsers);
    }

    public JoinedStudiesInfo getJoinedStudiesInfo(long userId) {
        List<StudyMember> studyMembers = studyMemberQueryRepository.findAllFetchStudyByUserId(userId);
        return JoinedStudiesInfo.of(convertToStudiesFrom(studyMembers));
    }

    @Transactional
    public long save(ReviewSaveServiceRequest request, long writeUserId) {
        if(isAlreadyWrittenReviewBy(request.getStudyId(), request.getTargetUserId(), writeUserId)){
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

    private Study findValidStudy(long studyId){
        return studyRepository.findById(studyId)
                .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND));
    }

    private List<StudyMember> findValidStudyMember(long studyId) {
        List<StudyMember> studyMembers = studyMemberRepository.findByStudyIdFetchStudy(studyId);

        if (studyMembers.isEmpty()) {
            throw new NotFoundException(STUDY_MEMBER_EMPTY);
        }
        return studyMembers;
    }

    private List<User> findReviewTargetUsers(List<StudyMember> studyMembers, String myNickname) {
        List<Long> userIds = studyMembers.stream()
                .map(StudyMember::getUserId)
                .toList();

        List<User> users = userRepository.findByIdIn(userIds);
        return users.stream()
                .filter(user -> user.isNotEqualsNickname(myNickname))
                .toList();
    }

    private List<Study> convertToStudiesFrom(List<StudyMember> studyMembers) {
        return studyMembers.stream()
                .map(StudyMember::getStudy)
                .toList();
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
