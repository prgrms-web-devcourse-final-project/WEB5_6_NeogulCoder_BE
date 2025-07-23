package grep.neogul_coder.domain.studypost.repository;

import grep.neogul_coder.domain.IntegrationTestSupport;
import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.study.repository.StudyMemberRepository;
import grep.neogul_coder.domain.study.repository.StudyRepository;
import grep.neogul_coder.domain.studypost.Category;
import grep.neogul_coder.domain.studypost.StudyPost;
import grep.neogul_coder.domain.studypost.comment.StudyPostComment;
import grep.neogul_coder.domain.studypost.comment.repository.StudyPostCommentRepository;
import grep.neogul_coder.domain.studypost.controller.dto.response.NoticePostInfo;
import grep.neogul_coder.domain.studypost.controller.dto.response.PostPagingInfo;
import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.domain.users.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static grep.neogul_coder.domain.studypost.Category.FREE;
import static grep.neogul_coder.domain.studypost.Category.NOTICE;
import static org.assertj.core.api.Assertions.assertThat;

class StudyPostQueryRepositoryTest extends IntegrationTestSupport {

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
    private StudyPostQueryRepository studyPostQueryRepository;

    @Autowired
    private StudyPostCommentRepository postCommentRepositoryPost;

    @DisplayName("스터디 게시글을 페이징 조회 합니다.")
    @Test
    void findPagingFilteredBy() {
        //given
        User user = createUser("테스터");
        userRepository.save(user);

        Study study = createStudy("자바 스터디");
        studyRepository.save(study);

        StudyPost post1 = createStudyPost(study, user.getId(), "제목1", NOTICE, "Like 내용1");
        StudyPost post2 = createStudyPost(study, user.getId(), "제목2", FREE, "Like 내용2");

        List<StudyPost> posts = List.of(
                post1, post2,
                createStudyPost(study, user.getId(), "제목3", FREE, "내용3"),
                createStudyPost(study, user.getId(), "제목4", NOTICE, "내용4"),
                createStudyPost(study, user.getId(), "제목5", FREE, "내용5")
        );
        studyPostRepository.saveAll(posts);

        List<StudyPostComment> comments = List.of(
                createPostComment(post1.getId(), user.getId(), "댓글1"),
                createPostComment(post2.getId(), user.getId(), "댓글2"),
                createPostComment(post2.getId(), user.getId(), "댓글3")
        );
        postCommentRepositoryPost.saveAll(comments);

        Sort sort = Sort.by(
                Sort.Order.desc("createDateTime"),
                Sort.Order.desc("commentCount")
        );
        PageRequest pageRequest = PageRequest.of(0, 2, sort);

        //when
        Page<PostPagingInfo> page = studyPostQueryRepository.findPagingFilteredBy(study.getId(), pageRequest, FREE, "Like");
        List<PostPagingInfo> response = page.getContent();
         // System.out.println("response = " + response);
         // System.out.println("page.getTotalPages() = " + page.getTotalPages());
         // System.out.println("page.getTotalElements() = " + page.getTotalElements());

        //then
        assertThat(response).hasSize(1);
        assertThat(response)
                .extracting("title", "commentCount")
                .containsExactly(
                        Tuple.tuple("제목2", 2L)
                );
    }

    @DisplayName("가장 최근 공지글을 조회 합니다.")
    @Test
    void findLatestNoticeInfoBy() {
        //given
        User user = createUser("테스터");
        userRepository.save(user);

        Study study = createStudy("자바 스터디");
        studyRepository.save(study);

        List<StudyPost> posts = List.of(
                createStudyPost(study, user.getId(), "공지글1", NOTICE, "내용1"),
                createStudyPost(study, user.getId(), "자유글2", FREE, "내용2"),
                createStudyPost(study, user.getId(), "공지글2", NOTICE, "내용3")
        );
        studyPostRepository.saveAll(posts);

        //when
        List<NoticePostInfo> result = studyPostQueryRepository.findLatestNoticeInfoBy(study.getId());

        //then
        assertThat(result).hasSize(2)
                .extracting("title")
                .containsExactlyInAnyOrder("공지글1", "공지글2");
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


    private StudyMember createStudyMember(Study study, long userId) {
        return StudyMember.builder()
                .study(study)
                .userId(userId)
                .build();
    }

    private StudyPost createStudyPost(Study study, long userId, String title, Category category, String content) {
        StudyPost studyPost = StudyPost.builder()
                .userId(userId)
                .title(title)
                .category(category)
                .content(content)
                .build();

        studyPost.connectStudy(study);
        return studyPost;
    }

    private StudyPostComment createPostComment(long postId, long userId, String content) {
        return StudyPostComment.builder()
                .postId(postId)
                .userId(userId)
                .content(content)
                .build();
    }
}