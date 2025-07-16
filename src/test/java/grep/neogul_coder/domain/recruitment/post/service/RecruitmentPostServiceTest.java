package grep.neogul_coder.domain.recruitment.post.service;

import grep.neogul_coder.domain.IntegrationTestSupport;
import grep.neogul_coder.domain.recruitment.RecruitmentPostStatus;
import grep.neogul_coder.domain.recruitment.post.RecruitmentPost;
import grep.neogul_coder.domain.recruitment.post.repository.RecruitmentPostRepository;
import grep.neogul_coder.domain.recruitment.post.service.request.RecruitmentPostCreateServiceRequest;
import grep.neogul_coder.domain.recruitment.post.service.request.RecruitmentPostStatusUpdateServiceRequest;
import grep.neogul_coder.domain.recruitment.post.service.request.RecruitmentPostUpdateServiceRequest;
import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.study.enums.Category;
import grep.neogul_coder.domain.study.enums.StudyMemberRole;
import grep.neogul_coder.domain.study.repository.StudyMemberRepository;
import grep.neogul_coder.domain.study.repository.StudyRepository;
import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.domain.users.repository.UserRepository;
import grep.neogul_coder.global.exception.business.BusinessException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

import static grep.neogul_coder.domain.study.enums.StudyMemberRole.LEADER;
import static grep.neogul_coder.domain.study.enums.StudyMemberRole.MEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RecruitmentPostServiceTest extends IntegrationTestSupport {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyMemberRepository studyMemberRepository;

    @Autowired
    private RecruitmentPostService recruitmentPostService;

    @Autowired
    private RecruitmentPostRepository recruitmentPostRepository;

    private long userId;
    private long recruitmentPostId;

    @BeforeEach
    void init() {
        User user = userRepository.save(createUser("테스터"));
        userId = user.getId();

        RecruitmentPost recruitmentPost = createRecruitmentPost("제목", "내용", 1, userId);
        recruitmentPostRepository.save(recruitmentPost);
        recruitmentPostId = recruitmentPost.getId();
    }

    @DisplayName("스터디 모집글을 작성 합니다.")
    @TestFactory
    Collection<DynamicTest> create() {

        Study study = createStudy("자바 스터디", Category.IT);
        studyRepository.save(study);

        return List.of(
                DynamicTest.dynamicTest("스터디의 리더는 모집글을 작성할 수 있습니다.", () -> {
                    //given
                    studyMemberRepository.save(createStudyMember(study, userId, LEADER));

                    RecruitmentPostCreateServiceRequest request =
                            createRecruitmentPostServiceRequest(study.getId(), "제목", "내용");

                    //when
                    long recruitmentPostId = recruitmentPostService.create(request, userId);

                    //then
                    RecruitmentPost findRecruitmentPost = recruitmentPostRepository.findById(recruitmentPostId).orElseThrow();
                    assertThat(findRecruitmentPost)
                            .extracting("userId", "subject", "content")
                            .containsExactly(userId, "제목", "내용");
                }),

                DynamicTest.dynamicTest("스터디의 리더가 아닌 회원은 모집글을 작성할 수 없습니다.", () -> {
                    //given
                    User member = createUser("맴버");
                    userRepository.save(member);

                    studyMemberRepository.save(createStudyMember(study, member.getId(), MEMBER));

                    RecruitmentPostCreateServiceRequest request =
                            createRecruitmentPostServiceRequest(study.getId(), "제목", "내용");

                    //when //then
                    assertThatThrownBy(() -> recruitmentPostService.create(request, member.getId()))
                            .isInstanceOf(BusinessException.class).hasMessage("스터디의 리더가 아닙니다.");
                })
        );
    }

    @DisplayName("모집글을 수정 합니다.")
    @TestFactory
    Collection<DynamicTest> update() {
        //given
        RecruitmentPostUpdateServiceRequest request = createUpdateRequest("수정된 제목", "수정된 내용", 2);

        return List.of(
                DynamicTest.dynamicTest("모집글 작성자 본인일 경우 수정 성공", () -> {
                    //when
                    recruitmentPostService.update(request, recruitmentPostId, userId);

                    //then
                    RecruitmentPost findRecruitmentPost = recruitmentPostRepository.findById(recruitmentPostId).orElseThrow();
                    assertThat(findRecruitmentPost)
                            .extracting("subject", "content", "recruitmentCount")
                            .containsExactly("수정된 제목", "수정된 내용", 2);
                }),

                DynamicTest.dynamicTest("모집글 작성자가 본인이 아닐 경우 예외 발생", () -> {
                    //given
                    Long otherUserId = -1L;

                    //when then
                    assertThatThrownBy(() ->
                            recruitmentPostService.update(request, recruitmentPostId, otherUserId))
                            .isInstanceOf(BusinessException.class).hasMessage("모집글을 등록한 당사자가 아닙니다.");
                })
        );
    }

    @DisplayName("모집글의 모집 상태를 변경 합니다.")
    @Test
    void updateStatus() {
        //given
        RecruitmentPostStatusUpdateServiceRequest request = new RecruitmentPostStatusUpdateServiceRequest(RecruitmentPostStatus.COMPLETE);

        //when
        recruitmentPostService.updateStatus(request, recruitmentPostId, userId);

        //then
        RecruitmentPost findRecruitmentPost = recruitmentPostRepository.findById(recruitmentPostId).orElseThrow();
        assertThat(findRecruitmentPost.getStatus()).isEqualTo(RecruitmentPostStatus.COMPLETE);
    }

    @DisplayName("모집글을 삭제 합니다.")
    @Test
    void delete() {
        //given
        RecruitmentPost savedRecruitmentPost = recruitmentPostRepository.findById(recruitmentPostId).orElseThrow();

        //when
        recruitmentPostService.delete(savedRecruitmentPost.getId(), userId);

        //then
        RecruitmentPost findRecruitmentPost = recruitmentPostRepository.findById(savedRecruitmentPost.getId()).orElseThrow();
        assertThat(findRecruitmentPost.getActivated()).isFalse();
    }

    private User createUser(String nickname) {
        return User.builder()
                .nickname(nickname)
                .build();
    }

    private Study createStudy(String name, Category category) {
        return Study.builder()
                .name(name)
                .category(category)
                .build();
    }

    private StudyMember createStudyMember(Study study, long userId, StudyMemberRole role) {
        return StudyMember.builder()
                .study(study)
                .userId(userId)
                .role(role)
                .build();
    }

    private RecruitmentPost createRecruitmentPost(String subject, String content, int count, long userId) {
        return RecruitmentPost.builder()
                .subject(subject)
                .content(content)
                .recruitmentCount(count)
                .userId(userId)
                .build();
    }

    private RecruitmentPostCreateServiceRequest createRecruitmentPostServiceRequest(long studyId, String subject, String content) {
        return RecruitmentPostCreateServiceRequest.builder()
                .studyId(studyId)
                .subject(subject)
                .content(content)
                .build();
    }

    private RecruitmentPostUpdateServiceRequest createUpdateRequest(String subject, String content, int count) {
        return RecruitmentPostUpdateServiceRequest.builder()
                .subject(subject)
                .content(content)
                .recruitmentCount(count)
                .build();
    }
}