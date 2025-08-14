package grep.neogulcoder.domain.recruitment.post.service;

import grep.neogulcoder.domain.IntegrationTestSupport;
import grep.neogulcoder.domain.recruitment.RecruitmentPostStatus;
import grep.neogulcoder.domain.recruitment.comment.RecruitmentPostComment;
import grep.neogulcoder.domain.recruitment.comment.repository.RecruitmentPostCommentRepository;
import grep.neogulcoder.domain.recruitment.post.RecruitmentPost;
import grep.neogulcoder.domain.recruitment.post.controller.dto.response.RecruitmentPostInfo;
import grep.neogulcoder.domain.recruitment.post.controller.dto.response.RecruitmentPostPagingInfo;
import grep.neogulcoder.domain.recruitment.post.repository.RecruitmentPostRepository;
import grep.neogulcoder.domain.recruitment.post.service.request.RecruitmentPostStatusUpdateServiceRequest;
import grep.neogulcoder.domain.recruitment.post.service.request.RecruitmentPostUpdateServiceRequest;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.enums.Category;
import grep.neogulcoder.domain.study.enums.StudyMemberRole;
import grep.neogulcoder.domain.study.enums.StudyType;
import grep.neogulcoder.domain.study.repository.StudyMemberRepository;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.domain.studyapplication.StudyApplication;
import grep.neogulcoder.domain.studyapplication.repository.ApplicationRepository;
import grep.neogulcoder.domain.users.entity.User;
import grep.neogulcoder.domain.users.repository.UserRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
import jakarta.persistence.EntityManager;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.Collection;
import java.util.List;

import static grep.neogulcoder.domain.recruitment.RecruitmentPostStatus.COMPLETE;
import static grep.neogulcoder.domain.recruitment.RecruitmentPostStatus.IN_PROGRESS;
import static grep.neogulcoder.domain.study.enums.StudyType.OFFLINE;
import static grep.neogulcoder.domain.study.enums.StudyType.ONLINE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RecruitmentPostServiceTest extends IntegrationTestSupport {

    @Autowired
    private EntityManager em;

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

    @Autowired
    private ApplicationRepository applicationRepository;

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

    @DisplayName("모집글의 정보를 조회 합니다.")
    @Test
    void get() {
        //given
        User user1 = createUser("테스터1");
        User user2 = createUser("테스터2");
        userRepository.saveAll(List.of(user1, user2));

        Study study1 = createStudy("자바 스터디", Category.IT, ONLINE);
        studyRepository.save(study1);

        RecruitmentPost post = createRecruitmentPost(study1.getId(), user1.getId(), "모집글 제목1", "내용1", IN_PROGRESS);
        recruitmentPostRepository.save(post);

        List<RecruitmentPostComment> comments = List.of(
                createPostComment(post, user1.getId(), "댓글1"),
                createPostComment(post, user2.getId(), "댓글2")
        );
        commentRepository.saveAll(comments);

        StudyApplication application1 = createStudyApplication(post.getId(), user1.getId(), "신청 사유");
        StudyApplication application2 = createStudyApplication(post.getId(), user2.getId(), "신청 사유2");
        applicationRepository.saveAll(List.of(application1, application2));

        //when
        RecruitmentPostInfo response = recruitmentPostService.get(post.getId());
        System.out.println("response = " + response);

        //then
        assertThat(response.getApplicationCount()).isEqualTo(2);
        assertThat(response.getPostDetailsInfo())
                .extracting("nickname", "subject", "category", "studyType")
                .containsExactly("테스터1", "모집글 제목1", "IT", "ONLINE");

        assertThat(response.getCommentsWithWriterInfos())
                .extracting("nickname", "content")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("테스터1", "댓글1"),
                        Tuple.tuple("테스터2", "댓글2")
                );
    }

    @DisplayName("모집글의 댓글 정보를 조회할때 탈퇴한 회원은 탈퇴한 회원이라고 표기 됩니다. ")
    @Test
    void get_WhenWithdrawnUser_ThenNicknameUpdate() {
        //given
        User user1 = createUser("테스터1");
        User user2 = createUser("테스터2");
        userRepository.saveAll(List.of(user1, user2));

        Study study1 = createStudy("자바 스터디", Category.IT, ONLINE);
        studyRepository.save(study1);

        RecruitmentPost post = createRecruitmentPost(study1.getId(), user1.getId(), "모집글 제목1", "내용1", IN_PROGRESS);
        recruitmentPostRepository.save(post);

        List<RecruitmentPostComment> comments = List.of(
                createPostComment(post, user1.getId(), "댓글1"),
                createPostComment(post, user2.getId(), "댓글2")
        );
        commentRepository.saveAll(comments);

        user1.delete();
        em.flush();
        em.clear();

        //when
        RecruitmentPostInfo response = recruitmentPostService.get(post.getId());

        //then
        assertThat(response.getCommentsWithWriterInfos())
                .extracting("nickname", "content")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("탈퇴한 회원", "댓글1"),
                        Tuple.tuple("테스터2", "댓글2")
                );
    }


    @DisplayName("모집글을 검색 합니다.")
    @Test
    void search() {
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
        RecruitmentPostPagingInfo result = recruitmentPostService.search(PageRequest.of(0, 2),
                Category.IT, ONLINE, null, null);
        System.out.println("result = " + result);

        //then
        assertThat(result.getPostInfos()).hasSize(1)
                .extracting("category", "subject", "commentCount")
                .containsExactlyInAnyOrder(
                        Tuple.tuple(Category.IT.name(), "모집글 제목1", 1)
                );
    }

    @DisplayName("내가 작성한 모집글을 검색 합니다.")
    @Test
    void search_WhenWrittenByUser() {
        //given
        User myUser = createUser("myNickname");
        User user2 = createUser("회원");
        userRepository.saveAll(List.of(myUser, user2));

        Study study1 = createStudy("자바 스터디", Category.IT, ONLINE);
        Study study2 = createStudy("클라이밍 동아리", Category.HOBBY, OFFLINE);
        studyRepository.saveAll(List.of(study1, study2));

        RecruitmentPost post1 = createRecruitmentPost(study1.getId(), user2.getId(), "모집글 제목1", "내용1", IN_PROGRESS);
        RecruitmentPost post2 = createRecruitmentPost(study2.getId(), myUser.getId(), "모집글 제목2", "내용2", IN_PROGRESS);
        RecruitmentPost post3 = createRecruitmentPost(study2.getId(), myUser.getId(), "모집글 제목3", "내용3", IN_PROGRESS);
        recruitmentPostRepository.saveAll(List.of(post1, post2, post3));

        List<RecruitmentPostComment> comments = List.of(
                createPostComment(post1, user2.getId(), "댓글1"),
                createPostComment(post2, user2.getId(), "댓글2"),
                createPostComment(post2, userId, "댓글3")
        );
        commentRepository.saveAll(comments);

        //when
        RecruitmentPostPagingInfo result = recruitmentPostService.search(PageRequest.of(0, 2),
                Category.HOBBY, OFFLINE, null, myUser.getId());
        System.out.println("result = " + result);

        //then
        assertThat(result.getPostInfos()).hasSize(2)
                .extracting("category", "subject", "commentCount")
                .containsExactlyInAnyOrder(
                        Tuple.tuple(Category.HOBBY.name(), "모집글 제목2", 2),
                        Tuple.tuple(Category.HOBBY.name(), "모집글 제목3", 0)
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
        assertThat(findRecruitmentPost.isActivated()).isFalse();
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

    private StudyApplication createStudyApplication(long postId, long userId, String reason) {
        return StudyApplication.builder()
                .recruitmentPostId(postId)
                .applicationReason(reason)
                .userId(userId)
                .isRead(false)
                .build();
    }
}