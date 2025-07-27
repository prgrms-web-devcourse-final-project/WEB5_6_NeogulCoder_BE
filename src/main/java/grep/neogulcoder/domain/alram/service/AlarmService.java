package grep.neogulcoder.domain.alram.service;

import grep.neogulcoder.domain.alram.controller.dto.response.AlarmResponse;
import grep.neogulcoder.domain.alram.entity.Alarm;
import grep.neogulcoder.domain.alram.exception.code.AlarmErrorCode;
import grep.neogulcoder.domain.alram.repository.AlarmRepository;
import grep.neogulcoder.domain.alram.type.AlarmType;
import grep.neogulcoder.domain.alram.type.DomainType;
import grep.neogulcoder.domain.recruitment.post.RecruitmentPost;
import grep.neogulcoder.domain.recruitment.post.repository.RecruitmentPostRepository;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.enums.StudyMemberRole;
import grep.neogulcoder.domain.study.event.StudyExtendEvent;
import grep.neogulcoder.domain.study.event.StudyExtensionReminderEvent;
import grep.neogulcoder.domain.study.event.StudyInviteEvent;
import grep.neogulcoder.domain.study.repository.StudyMemberQueryRepository;
import grep.neogulcoder.domain.study.repository.StudyMemberRepository;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.domain.studyapplication.StudyApplication;
import grep.neogulcoder.domain.studyapplication.event.ApplicationEvent;
import grep.neogulcoder.domain.studyapplication.event.ApplicationStatusChangedEvent;
import grep.neogulcoder.domain.studyapplication.repository.ApplicationRepository;
import grep.neogulcoder.domain.timevote.event.TimeVotePeriodCreatedEvent;
import grep.neogulcoder.global.exception.business.BusinessException;
import grep.neogulcoder.global.exception.business.NotFoundException;
import grep.neogulcoder.global.provider.finder.MessageFinder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static grep.neogulcoder.domain.recruitment.RecruitmentErrorCode.NOT_FOUND;
import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.*;
import static grep.neogulcoder.domain.studyapplication.exception.code.ApplicationErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final MessageFinder messageFinder;
    private final StudyRepository studyRepository;
    private final StudyMemberQueryRepository studyMemberQueryRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final RecruitmentPostRepository recruitmentPostRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional
    public void saveAlarm(Long receiverId, AlarmType alarmType, DomainType domainType,
        Long domainId) {
        String message = messageFinder.findMessage(alarmType, domainType, domainId);
        alarmRepository.save(Alarm.init(alarmType, receiverId, domainType, domainId, message));
    }

    public List<AlarmResponse> getAllUncheckedAlarms(Long receiverUserId) {
        return alarmRepository.findAllByReceiverUserIdAndCheckedFalse(receiverUserId).stream()
            .map(alarm -> AlarmResponse.toResponse(
                alarm.getId(),
                alarm.getReceiverUserId(),
                alarm.getAlarmType(),
                alarm.getDomainType(),
                alarm.getDomainId(),
                alarm.getMessage(),
                alarm.isChecked(),
                alarm.getCreatedDate()))
            .toList();
    }

    public List<AlarmResponse> getAllAlarms(Long receiverUserId) {
        return alarmRepository.findAllByReceiverUserId(receiverUserId).stream()
            .map(alarm -> AlarmResponse.toResponse(
                alarm.getId(),
                alarm.getReceiverUserId(),
                alarm.getAlarmType(),
                alarm.getDomainType(),
                alarm.getDomainId(),
                alarm.getMessage(),
                alarm.isChecked(),
                alarm.getCreatedDate()))
            .toList();
    }

    @Transactional
    public void checkAllAlarmWithoutInvite(Long receiverUserId) {
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
        StudyMember.createMember(study, targetUserId);
        alarm.checkAlarm();
    }

    @Transactional
    public void rejectInvite(Long alarmId) {
        Alarm alarm = findValidAlarm(alarmId);
        alarm.checkAlarm();
    }

    @EventListener
    public void handleStudyExtendEvent(StudyExtendEvent event) {
        List<StudyMember> members = studyMemberRepository.findAllByStudyIdAndActivatedTrue(
            event.studyId());

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

    @EventListener
    public void handleStudyExtensionReminderEvent(StudyExtensionReminderEvent event) {
        StudyMember leader = studyMemberRepository.findByStudyIdAndRoleAndActivatedTrue(
                event.studyId(), StudyMemberRole.LEADER)
            .orElseThrow(() -> new BusinessException(STUDY_LEADER_NOT_FOUND));

        saveAlarm(
            leader.getUserId(),
            AlarmType.STUDY_EXTENSION_REMINDER,
            DomainType.STUDY,
            event.studyId()
        );
    }

    @EventListener
    public void handleTimeVotePeriodCreatedEvent(TimeVotePeriodCreatedEvent event) {
        List<StudyMember> members = studyMemberRepository.findAllByStudyIdAndActivatedTrue(
            event.studyId());

        for (StudyMember member : members) {
            if (!member.getUserId().equals(event.excludedUserId())) {
                saveAlarm(
                    member.getUserId(),
                    AlarmType.TIME_VOTE_REQUEST,
                    DomainType.TIME_VOTE,
                    event.studyId()
                );
            }
        }
    }

    @EventListener
    public void handleApplicationEvent(ApplicationEvent event) {
        RecruitmentPost recruitmentPost = recruitmentPostRepository.findByIdAndActivatedTrue(event.recruitmentPostId())
            .orElseThrow(() -> new BusinessException(NOT_FOUND));

        saveAlarm(
            recruitmentPost.getUserId(),
            AlarmType.STUDY_APPLICATION,
            DomainType.RECRUITMENT_POST,
            event.recruitmentPostId()
        );
    }

    @EventListener
    public void handleApplicationStatusChangedEvent(ApplicationStatusChangedEvent event) {
        StudyApplication application = applicationRepository.findByIdAndActivatedTrue(event.applicationId())
            .orElseThrow(() -> new BusinessException(APPLICATION_NOT_FOUND));

        saveAlarm(
            application.getUserId(),
            event.alarmType(),
            DomainType.STUDY_APPLICATION,
            application.getId()
        );
    }

    private Alarm findValidAlarm(Long alarmId) {
        return alarmRepository.findById(alarmId)
            .orElseThrow(() -> new NotFoundException(AlarmErrorCode.ALARM_NOT_FOUND));
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
