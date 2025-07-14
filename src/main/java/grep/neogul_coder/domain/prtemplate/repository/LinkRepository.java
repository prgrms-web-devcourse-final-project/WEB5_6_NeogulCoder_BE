package grep.neogul_coder.domain.prtemplate.repository;

import grep.neogul_coder.domain.prtemplate.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

}
