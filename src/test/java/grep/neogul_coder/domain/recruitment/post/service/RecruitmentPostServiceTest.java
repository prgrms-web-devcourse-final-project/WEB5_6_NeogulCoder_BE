package grep.neogul_coder.domain.recruitment.post.service;

import grep.neogul_coder.domain.IntegrationTestSupport;
import grep.neogul_coder.domain.recruitment.RecruitmentPostStatus;
import grep.neogul_coder.domain.recruitment.post.RecruitmentPost;
import grep.neogul_coder.domain.recruitment.comment.RecruitmentPostComment;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.RecruitmentPostPagingInfo;
import grep.neogul_coder.domain.recruitment.comment.repository.RecruitmentPostCommentRepository;
import grep.neogul_coder.domain.recruitment.post.repository.RecruitmentPostRepository;
import grep.neogul_coder.domain.recruitment.post.service.request.RecruitmentPostCreateServiceRequest;
import grep.neogul_coder.domain.recruitment.post.service.request.RecruitmentPostStatusUpdateServiceRequest;
import grep.neogul_coder.domain.recruitment.post.service.request.RecruitmentPostUpdateServiceRequest;
import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.study.enums.Category;
import grep.neogul_coder.domain.study.enums.StudyMemberRole;
import grep.neogul_coder.domain.study.enums.StudyType;
import grep.neogul_coder.domain.study.repository.StudyMemberRepository;
import grep.neogul_coder.domain.study.repository.StudyRepository;
import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.domain.users.repository.UserRepository;
import grep.neogul_coder.global.exception.business.BusinessException;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.Collection;
import java.util.List;

import static grep.neogul_coder.domain.recruitment.RecruitmentPostStatus.COMPLETE;
import static grep.neogul_coder.domain.recruitment.RecruitmentPostStatus.IN_PROGRESS;
import static grep.neogul_coder.domain.study.enums.StudyType.OFFLINE;
import static grep.neogul_coder.domain.study.enums.StudyType.ONLINE;
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

    @Autowired
    private RecruitmentPostCommentRepository commentRepository;

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

    @DisplayName("모집글과 관련된 정보들을 페이징 조회 합니다.")
    @Test
    void getPagingInfo() {
        //given
        Study study1 = createStudy("자바 스터디", Category.IT, ONLINE);
        Study study2 = createStudy("클라이밍 동아리", Category.HOBBY, OFFLINE);
        studyRepository.saveAll(List.of(study1, study2));

        RecruitmentPost post1 = createRecruitmentPost(study1.getId(), userId, "모집글 제목1", "내용1", IN_PROGRESS);
        RecruitmentPost post2 = createRecruitmentPost(study2.getId(), userId, "모집글 제목2", "내용2", IN_PROGRESS);
        recruitmentPostRepository.saveAll(List.of(post1, post2));

        List<RecruitmentPostComment> comments = List.of(
                createPostComment(post1, userId, "댓글1"),
                createPostComment(post2, userId, "댓글2"),
                createPostComment(post2, userId, "댓글3")
        );
        commentRepository.saveAll(comments);

        //when
        PageRequest pageable = PageRequest.of(0, 2);
        RecruitmentPostPagingInfo result = recruitmentPostService.getPagingInfo(pageable);
        System.out.println("result = " + result);

        //then
        assertThat(result.getPostInfos()).hasSize(2)
                .extracting("category", "subject", "commentCount")
                .containsExactlyInAnyOrder(
                        Tuple.tuple(Category.IT.name(), "모집글 제목1", 1),
                        Tuple.tuple(Category.HOBBY.name(), "모집글 제목2", 2)
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
        RecruitmentPostStatusUpdateServiceRequest request = new RecruitmentPostStatusUpdateServiceRequest(COMPLETE);

        //when
        recruitmentPostService.updateStatus(request, recruitmentPostId, userId);

        //then
        RecruitmentPost findRecruitmentPost = recruitmentPostRepository.findById(recruitmentPostId).orElseThrow();
        assertThat(findRecruitmentPost.getStatus()).isEqualTo(COMPLETE);
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

    private Study createStudy(String name, Category category, StudyType studyType) {
        return Study.builder()
                .name(name)
                .category(category)
                .studyType(studyType)
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

    private RecruitmentPost createRecruitmentPost(long studyId, long userId, String subject,
                                                  String content, RecruitmentPostStatus status) {
        return RecruitmentPost.builder()
                .subject(subject)
                .studyId(studyId)
                .content(content)
                .status(status)
                .userId(userId)
                .build();
    }

    private RecruitmentPostComment createPostComment(RecruitmentPost post, long userId, String content) {
        return RecruitmentPostComment.builder()
                .recruitmentPost(post)
                .userId(userId)
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