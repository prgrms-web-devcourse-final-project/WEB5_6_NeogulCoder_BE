package grep.neogulcoder.domain.study.service;

import grep.neogulcoder.domain.study.Study;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudyManagementServiceFacade {

    private static final int MAX_RETRY = 3;

    public void decreaseMemberCount(Study study, Long userId) {
        int retry = 0;
        while (retry < MAX_RETRY) {
            try {
                study.decreaseMemberCount();
                return;
            } catch (OptimisticLockException | ObjectOptimisticLockingFailureException e) {
                retry++;

                try {
                    Thread.sleep(30);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        log.warn("스터디 currentCount 감소 실패 (studyId={}, userId={})", study.getId(), userId);
    }
}
