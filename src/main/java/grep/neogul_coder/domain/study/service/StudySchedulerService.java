package grep.neogul_coder.domain.study.service;

import grep.neogul_coder.domain.buddy.service.BuddyEnergyService;
import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.study.repository.StudyMemberRepository;
import grep.neogul_coder.domain.study.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
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

    public List<Study> findStudiesEndingIn7Days() {
        LocalDate targetEndDate = LocalDate.now().plusDays(7);
        LocalDateTime endDateStart = targetEndDate.atStartOfDay();
        LocalDateTime endDateEnd = targetEndDate.plusDays(1).atStartOfDay();

        return studyRepository.findStudiesEndingIn7Days(endDateStart, endDateEnd);
    }

    @Scheduled(fixedRate = 5000)
    @Transactional
    public void finalizeStudies() {
        LocalDateTime now = LocalDateTime.now();
        List<Study> studiesToBeFinished = studyRepository.findStudiesToBeFinished(now);

        for (Study study : studiesToBeFinished) {
            study.finish();

            // 스터디 멤버들 조회 후 버디에너지 업데이트
            List<StudyMember> members = studyMemberRepository.findByStudyIdFetchStudy(study.getId());
            for (StudyMember member : members) {
                buddyEnergyService.updateEnergyByStudy(member.getUserId(), member.isLeader());
            }
        }
    }
}
