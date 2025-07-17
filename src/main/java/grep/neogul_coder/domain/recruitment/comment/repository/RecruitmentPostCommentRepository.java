package grep.neogul_coder.domain.recruitment.comment.repository;

import grep.neogul_coder.domain.recruitment.comment.RecruitmentPostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitmentPostCommentRepository extends JpaRepository<RecruitmentPostComment, Long> {
}
