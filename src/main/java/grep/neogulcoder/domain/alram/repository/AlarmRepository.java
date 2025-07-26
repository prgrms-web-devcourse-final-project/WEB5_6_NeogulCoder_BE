package grep.neogulcoder.domain.alram.repository;

import grep.neogulcoder.domain.alram.entity.Alarm;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository  extends JpaRepository<Alarm, Long> {
    List<Alarm> findAllByReceiverUserIdAndCheckedFalse(Long receiverUserId);
    List<Alarm> findAllByReceiverUserId(Long receiverUserId);

}
