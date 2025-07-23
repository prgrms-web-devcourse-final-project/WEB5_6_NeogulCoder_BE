package grep.neogulcoder.domain.review.repository;

import grep.neogulcoder.domain.IntegrationTestSupport;
import grep.neogulcoder.domain.review.Review;
import grep.neogulcoder.domain.review.entity.ReviewEntity;
import grep.neogulcoder.domain.review.entity.ReviewTagEntity;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.enums.Category;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.domain.users.entity.User;
import grep.neogulcoder.domain.users.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private ReviewQueryRepository reviewQueryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @DisplayName("회원이 받은 주관리뷰를 페이징 조회 합니다.")
    @Test
    void findContentsPagingBy() {
        //given
        User reviewer = createUser("리뷰어");
        User targetUser = createUser("리뷰대상");
        userRepository.saveAll(List.of(reviewer, targetUser));

        Study study = createStudy("자바 스터디", Category.IT);
        studyRepository.save(study);

        List<ReviewEntity> reviews = List.of(
                createReviewEntity(createReview(study.getId(), targetUser.getId(), reviewer.getId(), "너무 친절 하세요"), new ArrayList<>()),
                createReviewEntity(createReview(study.getId(), targetUser.getId(), reviewer.getId(), "감사합니다"), new ArrayList<>()),
                createReviewEntity(createReview(study.getId(), targetUser.getId(), reviewer.getId(), null), new ArrayList<>())
        );
        reviewRepository.saveAll(reviews);

        //when
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<ReviewEntity> result = reviewQueryRepository.findContentsPagingBy(pageRequest, targetUser.getId());

        //then
        List<ReviewEntity> findReviews = result.getContent();
        assertThat(findReviews).hasSize(2)
                .extracting("content")
                .containsExactlyInAnyOrder("너무 친절 하세요", "감사합니다");
    }

    public User createUser(String nickname) {
        return User.builder()
                .nickname(nickname)
                .password("tempPassword")
                .build();
    }

    private Study createStudy(String name, Category category) {
        return Study.builder()
                .name(name)
                .category(category)
                .build();
    }

    private Review createReview(long studyId, long targetUserId, long writeUserId, String content) {
        return Review.builder()
                .studyId(studyId)
                .targetUserId(targetUserId)
                .writeUserId(writeUserId)
                .content(content)
                .build();
    }

    private ReviewEntity createReviewEntity(Review review, List<ReviewTagEntity> reviewTags) {
        return ReviewEntity.builder()
                .review(review)
                .reviewTags(reviewTags)
                .build();
    }
}