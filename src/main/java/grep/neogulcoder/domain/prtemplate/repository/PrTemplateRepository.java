package grep.neogulcoder.domain.prtemplate.repository;

import grep.neogulcoder.domain.prtemplate.entity.PrTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PrTemplateRepository extends JpaRepository<PrTemplate, Long> {

    Optional<PrTemplate> findById(Long id);
    Optional<PrTemplate> findByUserId(Long userId);
}
