package grep.neogul_coder.domain.studypost.repository;

import grep.neogul_coder.domain.studypost.StudyPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyPostRepository extends JpaRepository<StudyPost, Long> {

}
