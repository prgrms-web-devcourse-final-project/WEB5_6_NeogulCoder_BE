package grep.neogulcoder.domain.study.service;

import grep.neogulcoder.domain.buddy.service.BuddyEnergyService;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.event.StudyExtensionReminderEvent;
import grep.neogulcoder.domain.study.repository.StudyMemberRepository;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class StudySchedulerService {

    private final StudyRepository studyRepository;
    private final BuddyEnergyService buddyEnergyService;
    private final StudyMemberRepository studyMemberRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void findStudiesEndingIn7Days() {
        LocalDate targetEndDate = LocalDate.now().plusDays(7);
        LocalDateTime endDateStart = targetEndDate.atStartOfDay();
        LocalDateTime endDateEnd = targetEndDate.plusDays(1).atStartOfDay();

        List<Study> studies = studyRepository.findStudiesEndingIn7Days(endDateStart, endDateEnd);

        for (Study study : studies) {
            eventPublisher.publishEvent(new StudyExtensionReminderEvent(study.getId()));
        }
    }

    @Transactional
    public void finalizeStudies() {
        LocalDateTime now = LocalDateTime.now();
        List<Study> studiesToBeFinished = studyRepository.findStudiesToBeFinished(now);

        for (Study study : studiesToBeFinished) {
            study.finish();

            // 스터디 멤버들 조회 후 버디에너지 업데이트
            List<StudyMember> members = studyMemberRepository.findFetchStudyByStudyId(study.getId());
            for (StudyMember member : members) {
                buddyEnergyService.updateEnergyByStudy(member.getUserId(), member.isLeader());
            }
        }
    }
}
