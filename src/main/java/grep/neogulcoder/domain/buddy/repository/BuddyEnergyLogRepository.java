package grep.neogulcoder.domain.buddy.repository;

import grep.neogulcoder.domain.buddy.entity.BuddyLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuddyEnergyLogRepository extends JpaRepository<BuddyLog, Long> {

    // 특정 유저의 에너지 변동 로그 조회
    // (BuddyEnergy -> UserId로 매핑 필요할 수 있음)
    List<BuddyLog> findByBuddyEnergy_BuddyEnergyId(Long buddyEnergyId);
}
