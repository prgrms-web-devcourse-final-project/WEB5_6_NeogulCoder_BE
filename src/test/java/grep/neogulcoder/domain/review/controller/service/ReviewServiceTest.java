package grep.neogulcoder.domain.review.controller.service;

import grep.neogulcoder.domain.IntegrationTestSupport;
import grep.neogulcoder.domain.review.Review;
import grep.neogulcoder.domain.review.ReviewType;
import grep.neogulcoder.domain.review.controller.dto.response.ReviewTargetStudiesInfo;
import grep.neogulcoder.domain.review.controller.dto.response.MyReviewTagsInfo;
import grep.neogulcoder.domain.review.controller.dto.response.ReviewContentsPagingInfo;
import grep.neogulcoder.domain.review.controller.dto.response.ReviewTargetUsersInfo;
import grep.neogulcoder.domain.review.entity.MyReviewTagEntity;
import grep.neogulcoder.domain.review.entity.ReviewEntity;
import grep.neogulcoder.domain.review.entity.ReviewTagEntity;
import grep.neogulcoder.domain.review.repository.MyReviewTagRepository;
import grep.neogulcoder.domain.review.repository.ReviewRepository;
import grep.neogulcoder.domain.review.repository.ReviewTagRepository;
import grep.neogulcoder.domain.review.service.ReviewService;
import grep.neogulcoder.domain.review.service.request.ReviewSaveServiceRequest;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.enums.Category;
import grep.neogulcoder.domain.study.repository.StudyMemberRepository;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.domain.users.entity.User;
import grep.neogulcoder.domain.users.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static grep.neogulcoder.domain.review.BadReviewTag.LACK_OF_RESPONSIBILITY;
import static grep.neogulcoder.domain.review.GoodReviewTag.GOOD_ADAPTATION;
import static grep.neogulcoder.domain.review.ReviewType.BAD;
import static grep.neogulcoder.domain.review.ReviewType.GOOD;
import static org.assertj.core.api.Assertions.assertThat;

class ReviewServiceTest extends IntegrationTestSupport {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyMemberRepository studyMemberRepository;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MyReviewTagRepository myReviewTagRepository;

    @Autowired
    private ReviewTagRepository reviewTagRepository;

    @DisplayName("리뷰 대상 회원들의 정보를 조회할때 자신은 제외 된다.")
    @Test
    void getReviewTargetUsersInfo() {
        //given
        User myUser = createUser("내 닉네임");
        User user2 = createUser("테스터2");
        User user3 = createUser("테스터3");
        userRepository.saveAll(List.of(myUser, user2, user3));

        Study study = createStudy("운영체제 스터디", Category.IT);
        studyRepository.save(study);

        List<StudyMember> studyMembers = List.of(
                createStudyMember(study, myUser.getId()),
                createStudyMember(study, user2.getId()),
                createStudyMember(study, user3.getId())
        );
        studyMemberRepository.saveAll(studyMembers);

        //when
        ReviewTargetUsersInfo response = reviewService.getReviewTargetUsersInfo(study.getId(), myUser.getId());

        //then
        assertThat(response.getUserInfos()).hasSize(2)
                .extracting("nickname")
                .containsExactlyInAnyOrder("테스터2", "테스터3");
    }

    @DisplayName("리뷰 대상 회원들을 조회할때 이미 리뷰한 회원들은 제외 된다.")
    @Test
    void getReviewTargetUsersInfo_whenAlreadyReviewedUserExists_thenExcludeThem() {
        //given
        User myUser = createUser("내 닉네임");
        User user2 = createUser("테스터2");
        User user3 = createUser("테스터3");
        userRepository.saveAll(List.of(myUser, user2, user3));

        Study study = createStudy("운영체제 스터디", Category.IT);
        studyRepository.save(study);

        List<StudyMember> studyMembers = List.of(
                createStudyMember(study, myUser.getId()),
                createStudyMember(study, user2.getId()),
                createStudyMember(study, user3.getId())
        );
        studyMemberRepository.saveAll(studyMembers);

        reviewRepository.save(createReviewEntity(createReview(study.getId(), user2.getId(), myUser.getId()), Collections.emptyList()));

        //when
        ReviewTargetUsersInfo response = reviewService.getReviewTargetUsersInfo(study.getId(), myUser.getId());
        System.out.println("response = " + response);

        //then
        assertThat(response.getUserInfos()).hasSize(1)
                .extracting("nickname")
                .containsExactly("테스터3");
    }

    @DisplayName("리뷰 가능한 스터디 정보들을 조회 합니다.")
    @Test
    void getReviewTargetStudiesInfo() {
        //given
        User user = createUser("테스터");
        userRepository.save(user);

        LocalDateTime endDateTime = LocalDateTime.of(2025, 7, 1, 0, 0, 0);
        Study study = createStudy("운영체제 스터디", endDateTime);
        studyRepository.save(study);

        StudyMember studyMember1 = createStudyMember(study, user.getId());
        studyMemberRepository.save(studyMember1);

        //when
        LocalDateTime currentDateTime = endDateTime;
        ReviewTargetStudiesInfo response = reviewService.getReviewTargetStudiesInfo(user.getId(), currentDateTime);

        //then
        assertThat(response.getStudies()).hasSize(1)
                .extracting("studyName")
                .containsExactlyInAnyOrder("운영체제 스터디");
    }

    @DisplayName("리뷰 가능한 스터디를 조회 시, 없다면 빈 리스트를 반환한다.")
    @Test
    void getReviewTargetStudiesInfo_whenNotReviewableStudy_returnsEmptyList() {
        //given
        User user = createUser("테스터");
        userRepository.save(user);

        LocalDateTime endDateTime = LocalDateTime.of(2025, 7, 1, 0, 0, 0);
        Study study = createStudy("운영체제 스터디", endDateTime);
        studyRepository.save(study);

        StudyMember studyMember1 = createStudyMember(study, user.getId());
        studyMemberRepository.save(studyMember1);

        //when
        LocalDateTime currentDateTime = endDateTime.plusDays(7).plusSeconds(1);
        ReviewTargetStudiesInfo response = reviewService.getReviewTargetStudiesInfo(user.getId(), currentDateTime);

        //then
        assertThat(response.getStudies()).isEmpty();
    }

    @DisplayName("리뷰 입력을 받아 리뷰를 저장 합니다.")
    @Test
    void save() {
        //given
        User reviewer = createUser("리뷰어");
        User targetUser = createUser("리뷰대상");
        userRepository.saveAll(List.of(reviewer, targetUser));

        Study study = createStudy("자바 스터디", Category.IT);
        studyRepository.save(study);

        List<String> reviewTags = List.of(
                "주어진 역할은 무리 없이 잘 해냈어요.",
                "스터디 분위기를 잘 따라왔고, 팀에 잘 녹아들었어요."
        );

        ReviewSaveServiceRequest request = createReviewSaveRequest(study.getId(), targetUser.getId(), GOOD, reviewTags, "리뷰 내용");

        //when
        reviewService.save(request, reviewer.getId());

        //then
        List<ReviewEntity> reviews = reviewRepository.findByTargetUserId(targetUser.getId());
        assertThat(reviews).hasSize(1)
                .extracting("content")
                .containsExactly("리뷰 내용");

        List<MyReviewTagEntity> myReviewTags = reviews.getFirst().getReviewTags();
        assertThat(myReviewTags).hasSize(2);
    }

    @DisplayName("자신이 받은 리뷰 타입과 리뷰 태그들을 조회 합니다.")
    @Test
    void getMyReviewTags() {
        //given
        User reviewer = createUser("리뷰어");
        User targetUser = createUser("리뷰대상");
        userRepository.saveAll(List.of(reviewer, targetUser));

        Study study = createStudy("자바 스터디", Category.IT);
        studyRepository.save(study);

        ReviewTagEntity goodReviewTag = new ReviewTagEntity(GOOD, GOOD_ADAPTATION);
        ReviewTagEntity badReviewTag = new ReviewTagEntity(BAD, LACK_OF_RESPONSIBILITY);
        reviewTagRepository.saveAll(List.of(goodReviewTag, badReviewTag));

        List<ReviewTagEntity> myReviewTags = List.of(badReviewTag, goodReviewTag, goodReviewTag);
        Review review = createReview(study.getId(), targetUser.getId(), reviewer.getId());
        ReviewEntity reviewEntity = createReviewEntity(review, myReviewTags);
        reviewRepository.save(reviewEntity);

        //when
        MyReviewTagsInfo response = reviewService.getMyReviewTags(targetUser.getId());
        // System.out.println("response = " + response);

        //then
        assertThat(response.getReviewTypeMap().entrySet()).hasSize(2);
    }

    @DisplayName("자신이 받은 주관 리뷰들을 페이징 조회 합니다.")
    @Test
    void getMyReviewContents() {
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
        ReviewContentsPagingInfo result = reviewService.getMyReviewContents(pageRequest, targetUser.getId());

        //then
        assertThat(result.getReviewContents()).hasSize(2)
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

    private Study createStudy(String name, LocalDateTime endDate) {
        return Study.builder()
                .name(name)
                .endDate(endDate)
                .build();
    }

    private Review createReview(long studyId, long targetUserId, long writeUserId) {
        return Review.builder()
                .studyId(studyId)
                .targetUserId(targetUserId)
                .writeUserId(writeUserId)
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
                .studyId(review.getStudyId())
                .review(review)
                .reviewTags(reviewTags)
                .build();
    }

    private StudyMember createStudyMember(Study study, long userId) {
        return StudyMember.builder()
                .study(study)
                .userId(userId)
                .build();
    }

    private ReviewSaveServiceRequest createReviewSaveRequest(long studyId, long targetUserId, ReviewType reviewType,
                                                             List<String> reviewTags, String content) {
        return ReviewSaveServiceRequest.builder()
                .studyId(studyId)
                .targetUserId(targetUserId)
                .reviewType(reviewType)
                .reviewTag(reviewTags)
                .content(content)
                .build();
    }
}