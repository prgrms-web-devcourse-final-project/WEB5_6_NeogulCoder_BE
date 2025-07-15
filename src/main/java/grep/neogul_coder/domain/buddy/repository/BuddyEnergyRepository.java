package grep.neogul_coder.domain.buddy.repository;

import grep.neogul_coder.domain.buddy.entity.BuddyEnergy;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuddyEnergyRepository extends JpaRepository<BuddyEnergy, Long> {
    Optional<BuddyEnergy> findByUserId(Long userId);

}
