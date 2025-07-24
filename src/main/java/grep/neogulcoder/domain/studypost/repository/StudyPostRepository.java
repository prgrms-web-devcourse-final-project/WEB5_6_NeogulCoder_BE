package grep.neogulcoder.domain.studypost.repository;

import grep.neogulcoder.domain.studypost.StudyPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyPostRepository extends JpaRepository<StudyPost, Long> {
    int countByStudyIdAndActivatedTrue(Long studyId);
}
