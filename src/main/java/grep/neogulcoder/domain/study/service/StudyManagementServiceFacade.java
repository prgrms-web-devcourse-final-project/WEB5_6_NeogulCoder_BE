package grep.neogulcoder.domain.study.service;

import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.global.exception.business.BusinessException;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudyManagementServiceFacade {

    private static final int MAX_RETRY = 3;

    @Transactional
    public void increaseMemberCount(Study study, Long userId) {
        int retry = 0;
        while (retry < MAX_RETRY) {
            try {
                study.increaseMemberCount();
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

        log.warn("스터디 currentCount 증가 실패 (studyId={}, userId={})", study.getId(), userId);
        throw new BusinessException(STUDY_MEMBER_COUNT_UPDATE_FAILED);
    }

    @Transactional
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
        throw new BusinessException(STUDY_MEMBER_COUNT_UPDATE_FAILED);
    }
}
