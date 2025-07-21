package grep.neogul_coder.domain.studypost.comment.repository;

import grep.neogul_coder.domain.studypost.comment.StudyPostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyCommentRepository extends JpaRepository<StudyPostComment,Long> {
}
