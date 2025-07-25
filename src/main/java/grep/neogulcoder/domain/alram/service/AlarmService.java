package grep.neogulcoder.domain.alram.service;

import grep.neogulcoder.domain.alram.controller.dto.response.AlarmResponse;
import grep.neogulcoder.domain.alram.entity.Alarm;
import grep.neogulcoder.domain.alram.exception.code.AlarmErrorCode;
import grep.neogulcoder.domain.alram.repository.AlarmRepository;
import grep.neogulcoder.domain.alram.type.AlarmType;
import grep.neogulcoder.domain.alram.type.DomainType;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.event.StudyExtendEvent;
import grep.neogulcoder.domain.study.event.StudyInviteEvent;
import grep.neogulcoder.domain.study.repository.StudyMemberQueryRepository;
import grep.neogulcoder.domain.study.repository.StudyMemberRepository;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
import grep.neogulcoder.global.exception.business.NotFoundException;
import grep.neogulcoder.global.provider.finder.MessageFinder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.STUDY_NOT_FOUND;
import static grep.neogulcoder.domain.studyapplication.exception.code.ApplicationErrorCode.APPLICATION_PARTICIPANT_LIMIT_EXCEEDED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final MessageFinder messageFinder;
    private final StudyRepository studyRepository;
    private final StudyMemberQueryRepository studyMemberQueryRepository;
    private final StudyMemberRepository studyMemberRepository;

    @Transactional
    public void saveAlarm(Long receiverId, AlarmType alarmType, DomainType domainType, Long domainId) {
        String message = messageFinder.findMessage(alarmType, domainType, domainId);
        alarmRepository.save(Alarm.init(alarmType, receiverId, domainType, domainId, message));
    }

    public List<AlarmResponse> getAllAlarms(Long receiverUserId) {
        return alarmRepository.findAllByReceiverUserIdAndCheckedFalse(receiverUserId).stream()
            .map(alarm -> AlarmResponse.toResponse(
                alarm.getId(),
                alarm.getReceiverUserId(),
                alarm.getAlarmType(),
                alarm.getDomainType(),
                alarm.getDomainId(),
                alarm.getMessage()))
            .toList();
    }

    @Transactional
    public void checkAllAlarm(Long receiverUserId) {
        List<Alarm> alarms = alarmRepository.findAllByReceiverUserIdAndCheckedFalse(receiverUserId);
        alarms.stream()
            .filter(alarm -> alarm.getAlarmType() != AlarmType.INVITE)
            .forEach(Alarm::checkAlarm);
    }

    @EventListener
    public void handleStudyInviteEvent(StudyInviteEvent event) {
        saveAlarm(
            event.targetUserId(),
            AlarmType.INVITE,
            DomainType.STUDY,
            event.studyId()
        );
    }

    @Transactional
    public void acceptInvite(Long targetUserId, Long alarmId) {

        validateParticipantStudyLimit(targetUserId);

        Alarm alarm = findValidAlarm(alarmId);
        Long studyId = alarm.getDomainId();
        Study study = findValidStudy(studyId);
        StudyMember.createMember(study,targetUserId);
        alarm.checkAlarm();
    }

    @Transactional
    public void rejectInvite(Long alarmId) {
        Alarm alarm = findValidAlarm(alarmId);
        alarm.checkAlarm();
    }

    @EventListener
    public void handleStudyExtendEvent(StudyExtendEvent event) {
        List<StudyMember> members = studyMemberRepository.findAllByStudyIdAndActivatedTrue(event.studyId());

        for (StudyMember member : members) {
            if (!member.isLeader()) {
                saveAlarm(
                    member.getUserId(),
                    AlarmType.STUDY_EXTEND,
                    DomainType.STUDY,
                    event.studyId()
                );
            }
        }
    }

    private Alarm findValidAlarm(Long alarmId) {
        return alarmRepository.findById(alarmId).orElseThrow(() -> new NotFoundException(AlarmErrorCode.ALARM_NOT_FOUND));
    }

    private Study findValidStudy(Long studyId) {
        return studyRepository.findById(studyId)
            .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND));
    }

    private void validateParticipantStudyLimit(Long userId) {
        int count = studyMemberQueryRepository.countActiveUnfinishedStudies(userId);
        if (count >= 10) {
            throw new BusinessException(APPLICATION_PARTICIPANT_LIMIT_EXCEEDED);
        }
    }
}
