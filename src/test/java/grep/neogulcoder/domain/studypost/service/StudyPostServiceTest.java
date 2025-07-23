package grep.neogulcoder.domain.studypost.service;

import grep.neogulcoder.domain.IntegrationTestSupport;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.repository.StudyMemberRepository;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.domain.studypost.Category;
import grep.neogulcoder.domain.studypost.StudyPost;
import grep.neogulcoder.domain.studypost.comment.StudyPostComment;
import grep.neogulcoder.domain.studypost.comment.repository.StudyPostCommentRepository;
import grep.neogulcoder.domain.studypost.controller.dto.request.StudyPostSaveRequest;
import grep.neogulcoder.domain.studypost.controller.dto.request.StudyPostUpdateRequest;
import grep.neogulcoder.domain.studypost.controller.dto.response.StudyPostDetailResponse;
import grep.neogulcoder.domain.studypost.repository.StudyPostRepository;
import grep.neogulcoder.domain.users.entity.User;
import grep.neogulcoder.domain.users.repository.UserRepository;
import grep.neogulcoder.global.exception.business.NotFoundException;
import jakarta.persistence.EntityManager;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

import static grep.neogulcoder.domain.studypost.Category.FREE;
import static grep.neogulcoder.domain.studypost.Category.NOTICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StudyPostServiceTest extends IntegrationTestSupport {

    @Autowired
    private StudyPostService studyPostService;

    @Autowired
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyMemberRepository studyMemberRepository;

    @Autowired
    private StudyPostRepository studyPostRepository;

    @Autowired
    private StudyPostCommentRepository studyPostCommentRepository;

    @DisplayName("게시글을 조회 합니다.")
    @Test
    void findOne() {
        //given
        User user = createUser("테스터");
        userRepository.save(user);

        Study study = createStudy("자바 스터디");
        studyRepository.save(study);

        StudyPost post = createStudyPost(user.getId(), "제목", FREE, "내용");
        post.connectStudy(study);
        studyPostRepository.save(post);

        List<StudyPostComment> comments = List.of(
                createPostComment(post.getId(), user.getId(), "댓글1"),
                createPostComment(post.getId(), user.getId(), "댓글2"),
                createPostComment(post.getId(), user.getId(), "댓글3")
        );
        studyPostCommentRepository.saveAll(comments);

        //when
        StudyPostDetailResponse response = studyPostService.findOne(post.getId());

        //then
        assertThat(response.getPostInfo())
                .extracting("nickname", "title")
                .containsExactly("테스터", "제목");

        assertThat(response.getCommentCount()).isEqualTo(3);

        assertThat(response.getComments())
                .extracting("nickname", "content")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("테스터", "댓글1"),
                        Tuple.tuple("테스터", "댓글2"),
                        Tuple.tuple("테스터", "댓글3")
                );
    }

    @DisplayName("스터디 게시글을 생성 합니다.")
    @TestFactory
    Collection<DynamicTest> create() {
        User user = createUser("테스터");
        userRepository.save(user);

        Study study = createStudy("자바 스터디");
        studyRepository.save(study);

        return List.of(
                DynamicTest.dynamicTest("스터디 게시글은 스터디에 참여한 회원만 작성할 수 있습니다.", () -> {
                    StudyPostSaveRequest request = createStudyPostSaveRequest("게시글 제목", FREE, "게시글 내용");

                    //when //then
                    assertThatThrownBy(() -> studyPostService.create(request, study.getId(), user.getId()))
                            .isInstanceOf(NotFoundException.class).hasMessage("해당 스터디에 참여 하고 있지 않은 회원 입니다.");
                }),

                DynamicTest.dynamicTest("스터디 게시글을 생성 합니다.", () -> {
                    //given
                    StudyMember studyMember = createStudyMember(study, user.getId());
                    studyMemberRepository.save(studyMember);

                    StudyPostSaveRequest request = createStudyPostSaveRequest("게시글 제목", FREE, "게시글 내용");

                    //when
                    long postId = studyPostService.create(request, study.getId(), user.getId());
                    em.flush();
                    em.clear();

                    //then
                    StudyPost studyPost = studyPostRepository.findById(postId).orElseThrow();
                    assertThat(studyPost)
                            .extracting("title", "content")
                            .containsExactly("게시글 제목", "게시글 내용");
                })
        );
    }

    @DisplayName("스터디 게시글을 수정 합니다")
    @Test
    void update() {
        //given
        User user = createUser("테스터");
        userRepository.save(user);

        Study study = createStudy("자바 스터디");
        studyRepository.save(study);

        StudyPost post = createStudyPost(user.getId(), "제목", FREE, "내용");
        post.connectStudy(study);
        studyPostRepository.save(post);

        StudyPostUpdateRequest request = createUpdateRequest("수정된 제목", NOTICE, "수정된 내용");

        //when
        studyPostService.update(request, post.getId(), user.getId());
        em.flush();
        em.clear();

        //then
        StudyPost findPost = studyPostRepository.findById(post.getId()).orElseThrow();
        assertThat(findPost)
                .extracting("title", "category", "content")
                .containsExactly("수정된 제목", NOTICE, "수정된 내용");
    }

    @DisplayName("게시글을 삭제 합니다.")
    @Test
    void delete() {
        //given
        User user = createUser("테스터");
        userRepository.save(user);

        Study study = createStudy("자바 스터디");
        studyRepository.save(study);

        StudyPost post = createStudyPost(user.getId(), "제목", FREE, "내용");
        post.connectStudy(study);
        studyPostRepository.save(post);

        //when
        studyPostService.delete(post.getId(), user.getId());
        em.flush();
        em.clear();

        //then
        StudyPost findPost = studyPostRepository.findById(post.getId()).orElseThrow();
        assertThat(findPost.getActivated()).isFalse();
    }

    private User createUser(String nickname) {
        return User.builder()
                .nickname(nickname)
                .password("tempPassword")
                .build();
    }

    private Study createStudy(String name) {
        return Study.builder()
                .name(name)
                .build();
    }

    private StudyPostSaveRequest createStudyPostSaveRequest(String title, Category category, String content) {
        return StudyPostSaveRequest.builder()
                .title(title)
                .category(category)
                .content(content)
                .build();
    }

    private StudyMember createStudyMember(Study study, long userId) {
        return StudyMember.builder()
                .study(study)
                .userId(userId)
                .build();
    }

    private StudyPost createStudyPost(long userId, String title, Category category, String content) {
        return StudyPost.builder()
                .userId(userId)
                .title(title)
                .category(category)
                .content(content)
                .build();
    }

    private StudyPostUpdateRequest createUpdateRequest(String title, Category category, String content) {
        return StudyPostUpdateRequest.builder()
                .title(title)
                .category(category)
                .content(content)
                .build();
    }

    private StudyPostComment createPostComment(long postId, long userId, String content) {
        return StudyPostComment.builder()
                .postId(postId)
                .userId(userId)
                .content(content)
                .build();
    }
}