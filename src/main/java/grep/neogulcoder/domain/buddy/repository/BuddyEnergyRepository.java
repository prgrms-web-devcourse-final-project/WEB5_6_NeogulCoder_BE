package grep.neogulcoder.domain.buddy.repository;

import grep.neogulcoder.domain.buddy.entity.BuddyEnergy;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuddyEnergyRepository extends JpaRepository<BuddyEnergy, Long> {
    Optional<BuddyEnergy> findByUserId(Long userId);

}
