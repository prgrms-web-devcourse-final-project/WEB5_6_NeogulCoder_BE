package grep.neogulcoder.domain.study.service;

import grep.neogulcoder.global.exception.business.BusinessException;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.STUDY_MEMBER_COUNT_UPDATE_FAILED;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudyManagementServiceFacade {

    private final StudyMemberCountService studyMemberCountService;
    private static final int MAX_RETRY = 3;

    public void increaseMemberCount(Long studyId, Long userId) {
        int retry = 0;
        while (retry < MAX_RETRY) {
            try {
                studyMemberCountService.increaseMemberCount(studyId);
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

        log.warn("스터디 currentCount 증가 실패 (studyId={}, userId={})", studyId, userId);
        throw new BusinessException(STUDY_MEMBER_COUNT_UPDATE_FAILED);
    }

    public void decreaseMemberCount(Long studyId, Long userId) {
        int retry = 0;
        while (retry < MAX_RETRY) {
            try {
                studyMemberCountService.decreaseMemberCount(studyId);
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

        log.warn("스터디 currentCount 감소 실패 (studyId={}, userId={})", studyId, userId);
        throw new BusinessException(STUDY_MEMBER_COUNT_UPDATE_FAILED);
    }
}
