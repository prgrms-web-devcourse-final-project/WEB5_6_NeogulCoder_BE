package grep.neogulcoder.domain.studypost.comment.repository;

import grep.neogulcoder.domain.studypost.comment.StudyPostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyPostCommentRepository extends JpaRepository<StudyPostComment,Long> {
}
