package grep.neogul_coder.domain.study.service;

import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
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

    public List<Study> findStudiesEndingIn7Days() {
        LocalDate targetEndDate = LocalDate.now().plusDays(7);
        LocalDateTime endDateStart = targetEndDate.atStartOfDay();
        LocalDateTime endDateEnd = targetEndDate.plusDays(1).atStartOfDay();

        return studyRepository.findStudiesEndingIn7Days(endDateStart, endDateEnd);
    }

    @Transactional
    public void finalizeStudies() {
        LocalDateTime now = LocalDateTime.now();
        List<Study> studiesToBeFinished = studyRepository.findStudiesToBeFinished(now);

        for (Study study : studiesToBeFinished) {
            study.finish();
        }
    }
}
