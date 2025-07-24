package grep.neogulcoder.domain.alram.service;

import grep.neogulcoder.domain.alram.entity.Alarm;
import grep.neogulcoder.domain.alram.repository.AlarmRepository;
import grep.neogulcoder.domain.alram.type.AlarmType;
import grep.neogulcoder.domain.alram.type.DomainType;
import grep.neogulcoder.global.provider.finder.MessageFinder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final MessageFinder messageFinder;

    @Transactional
    public void saveAlarm(Long receiverId, AlarmType alarmType, DomainType domainType, Long domainId) {
        String message = messageFinder.findMessage(alarmType);
        alarmRepository.save(Alarm.init(alarmType, receiverId, domainType, domainId, message));
    }

    public List<Alarm> getAllAlarms(Long receiverUserId) {
        return alarmRepository.findAllByReceiverUserIdAndActivatedTrue(receiverUserId);
    }

    @Transactional
    public void readAllAlarm(Long receiverUserId) {
        List<Alarm> alarms = alarmRepository.findAllByReceiverUserIdAndActivatedTrue(receiverUserId);
        alarms.forEach(Alarm::read);
    }

}
