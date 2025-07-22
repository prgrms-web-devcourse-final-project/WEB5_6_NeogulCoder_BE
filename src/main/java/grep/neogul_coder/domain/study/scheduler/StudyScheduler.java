package grep.neogul_coder.domain.study.scheduler;

import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.service.StudySchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StudyScheduler {

    private final StudySchedulerService studySchedulerService;

    @Scheduled(cron = "0 0 0 * * *")
    public void processEndingStudies() {
        List<Study> studiesEndingIn7Days = studySchedulerService.findStudiesEndingIn7Days();

        studySchedulerService.finalizeStudies();
    }
}
