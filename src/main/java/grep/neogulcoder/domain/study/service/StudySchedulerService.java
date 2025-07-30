package grep.neogulcoder.domain.study.service;

import grep.neogulcoder.domain.buddy.entity.BuddyEnergy;
import grep.neogulcoder.domain.buddy.repository.BuddyEnergyRepository;
import grep.neogulcoder.domain.buddy.service.BuddyEnergyService;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.event.StudyExtensionReminderEvent;
import grep.neogulcoder.domain.study.repository.StudyMemberQueryRepository;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class StudySchedulerService {

    private final StudyRepository studyRepository;
    private final BuddyEnergyService buddyEnergyService;
    private final ApplicationEventPublisher eventPublisher;
    private final StudyMemberQueryRepository studyMemberQueryRepository;
    private final BuddyEnergyRepository buddyEnergyRepository;

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

        Map<Long, List<StudyMember>> memberMap = getActivatedMemberMap(studiesToBeFinished);
        Map<Long, BuddyEnergy> energyMap = getBuddyEnergyMap(memberMap);

        for (Study study : studiesToBeFinished) {
            study.finish();

            List<StudyMember> members = memberMap.getOrDefault(study.getId(), List.of());
            for (StudyMember member : members) {
                BuddyEnergy energy = energyMap.get(member.getUserId());
                buddyEnergyService.updateEnergyByStudy(energy, member.isLeader());
            }
        }
    }

    private Map<Long, List<StudyMember>> getActivatedMemberMap(List<Study> studies) {
        List<Long> studyIds = studies.stream()
            .map(Study::getId)
            .toList();

        List<StudyMember> allActivatedMembers = studyMemberQueryRepository.findActivatedMembersByStudyIds(studyIds);

        return allActivatedMembers.stream()
            .collect(Collectors.groupingBy(studyMember -> studyMember.getStudy().getId()));
    }

    private Map<Long, BuddyEnergy> getBuddyEnergyMap(Map<Long, List<StudyMember>> memberMap) {
        Set<Long> userIds = memberMap.values().stream()
            .flatMap(List::stream)
            .map(StudyMember::getUserId)
            .collect(Collectors.toSet());

        Map<Long, BuddyEnergy> energyMap = buddyEnergyRepository.findAllByUserIdIn(userIds).stream()
            .collect(Collectors.toMap(
                BuddyEnergy::getUserId,
                energy -> energy
            ));

        return energyMap;
    }
}
