package grep.neogul_coder.domain.study.repository;

import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface StudyRepository extends JpaRepository<Study, Long> {
    List<Study> findByIdIn(List<Long> studyIds);

    Optional<Study> findByIdAndActivatedTrue(Long studyId);

    Optional<Study> findByOriginStudyIdAndActivatedTrue(Long originStudyId);
}
