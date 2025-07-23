package grep.neogulcoder.domain.recruitment.comment.repository;

import grep.neogulcoder.domain.recruitment.comment.RecruitmentPostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitmentPostCommentRepository extends JpaRepository<RecruitmentPostComment, Long> {
}
