package grep.neogul_coder.domain.review.controller.service;

import grep.neogul_coder.domain.review.*;
import grep.neogul_coder.domain.review.controller.dto.response.ReviewTargetUsersInfo;
import grep.neogul_coder.domain.review.controller.service.request.ReviewSaveServiceRequest;
import grep.neogul_coder.domain.review.entity.ReviewEntity;
import grep.neogul_coder.domain.review.repository.ReviewQueryRepository;
import grep.neogul_coder.domain.review.repository.ReviewRepository;
import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.study.repository.StudyMemberRepository;
import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.domain.users.repository.UserRepository;
import grep.neogul_coder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static grep.neogul_coder.domain.review.ReviewErrorCode.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewTagFinder reviewTagFinder;
    private final UserRepository userRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewQueryRepository reviewQueryRepository;

    public ReviewTargetUsersInfo getTargetUsersInfo(long studyId, String myNickname) {
        List<StudyMember> studyMembers = studyMemberRepository.findByStudyIdFetchStudy(studyId);
        List<User> targetUsers = findReviewTargetUsers(studyMembers, myNickname);
        Study study = extractStudyFrom(studyMembers);

        return ReviewTargetUsersInfo.of(study, targetUsers);
    }

    @Transactional
    public void save(ReviewSaveServiceRequest request, long userId) {
        ReviewTags reviewTags = ReviewTags.from(convertStringToReviewTag(request.getReviewTag()));
        ReviewType reviewType = reviewTags.getReviewType();

        Review review = request.toReview(reviewTags.getReviewTags(), reviewType, userId);
        reviewRepository.save(ReviewEntity.from(review));
    }

    public void getMyReviewTags(long userId) {
        List<ReviewEntity> myReviews = reviewRepository.findByTargetUserId(userId);
        List<Long> reviewIds = myReviews.stream()
                .map(ReviewEntity::getId)
                .toList();

        reviewQueryRepository.findMyReviewTagFetchAll(reviewIds);
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

    private Study extractStudyFrom(List<StudyMember> studyMembers) {
        if(studyMembers.isEmpty()){
            throw new NotFoundException(STUDY_MEMBER_EMPTY, STUDY_MEMBER_EMPTY.getMessage());
        }
        return studyMembers.getFirst().getStudy();
    }

    private List<ReviewTag> convertStringToReviewTag(List<String> reviewTags) {
        return reviewTags.stream()
                .map(reviewTagFinder::findBy)
                .toList();
    }
}
