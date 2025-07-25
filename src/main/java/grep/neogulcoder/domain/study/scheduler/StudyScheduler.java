package grep.neogulcoder.domain.study.scheduler;

import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.service.StudySchedulerService;
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
        studySchedulerService.findStudiesEndingIn7Days();

        studySchedulerService.finalizeStudies();
    }
}
