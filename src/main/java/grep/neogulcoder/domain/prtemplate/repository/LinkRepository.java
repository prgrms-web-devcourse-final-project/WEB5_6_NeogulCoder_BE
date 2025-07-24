package grep.neogulcoder.domain.prtemplate.repository;

import grep.neogulcoder.domain.prtemplate.entity.Link;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    Link findByUserId(Long prId);
    List<Link> findAllByUserIdAndActivatedTrue(Long prId);
    List<Link> findAllByUserId(Long userId);
}
