package grep.neogulcoder.domain.alram.repository;

import grep.neogulcoder.domain.alram.entity.Alarm;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository  extends JpaRepository<Alarm, Long> {
    List<Alarm> findAllByReceiverUserIdAndCheckedFalse(Long receiverUserId);
    List<Alarm> findAllByReceiverUserId(Long receiverUserId);
    Optional<Alarm> findByReceiverUserIdAndId(Long targetUserId, Long alarmId);
}
