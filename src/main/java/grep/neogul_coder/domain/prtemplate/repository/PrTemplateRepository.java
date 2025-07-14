package grep.neogul_coder.domain.prtemplate.repository;

import grep.neogul_coder.domain.prtemplate.entity.PrTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PrTemplateRepository extends JpaRepository<PrTemplate, Long> {
    PrTemplate findByUserId(Long userId);

}
