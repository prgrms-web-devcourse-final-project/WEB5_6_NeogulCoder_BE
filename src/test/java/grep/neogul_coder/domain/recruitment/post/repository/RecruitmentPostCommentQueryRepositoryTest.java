package grep.neogul_coder.domain.recruitment.post.repository;

import grep.neogul_coder.domain.IntegrationTestSupport;
import grep.neogul_coder.domain.recruitment.comment.repository.RecruitmentPostCommentQueryRepository;
import grep.neogul_coder.domain.recruitment.comment.repository.RecruitmentPostCommentRepository;
import grep.neogul_coder.domain.recruitment.post.RecruitmentPost;
import grep.neogul_coder.domain.recruitment.comment.RecruitmentPostComment;
import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.enums.Category;
import grep.neogul_coder.domain.study.repository.StudyRepository;
import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.domain.users.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RecruitmentPostCommentQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private RecruitmentPostRepository recruitmentPostRepository;

    @Autowired
    private RecruitmentPostCommentQueryRepository commentQueryRepository;

    @Autowired
    private RecruitmentPostCommentRepository commentRepository;

    @DisplayName("해당 모집글의 댓글들을 조회 합니다.")
    @Test
    void findByPostIdIn() {
        //given
        User user1 = createUser("테스터");
        User user2 = createUser("테스터");
        userRepository.saveAll(List.of(user1, user2));

        Study study = createStudy("자바 스터디", Category.IT);
        studyRepository.save(study);

        RecruitmentPost recruitmentPost = createRecruitmentPost(study.getId(), user1.getId(), "제목", "내용");
        recruitmentPostRepository.save(recruitmentPost);

        List<RecruitmentPostComment> comments = List.of(
                createRecruitmentPostComment(recruitmentPost, user1.getId(), "댓글 내용1"),
                createRecruitmentPostComment(recruitmentPost, user2.getId(), "댓글 내용2")
        );
        commentRepository.saveAll(comments);

        //when
        List<RecruitmentPostComment> findComments = commentQueryRepository.findByPostIdIn(List.of(recruitmentPost.getId()));

        //then
        assertThat(findComments).hasSize(2)
                .extracting("content")
                .containsExactlyInAnyOrder("댓글 내용1", "댓글 내용2");
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

    private RecruitmentPost createRecruitmentPost(long studyId, long userId, String subject, String content) {
        return RecruitmentPost.builder()
                .studyId(studyId)
                .userId(userId)
                .subject(subject)
                .content(content)
                .build();
    }

    private RecruitmentPostComment createRecruitmentPostComment(RecruitmentPost recruitmentPost, long userId, String content) {
        return RecruitmentPostComment.builder()
                .recruitmentPost(recruitmentPost)
                .userId(userId)
                .content(content)
                .build();
    }
}