package grep.neogul_coder.domain.study.repository;

import grep.neogul_coder.domain.study.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
