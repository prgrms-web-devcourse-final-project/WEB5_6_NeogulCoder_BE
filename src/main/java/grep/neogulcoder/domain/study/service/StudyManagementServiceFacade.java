package grep.neogulcoder.domain.study.service;

import grep.neogulcoder.global.exception.business.BusinessException;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.STUDY_MEMBER_COUNT_UPDATE_FAILED;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudyManagementServiceFacade {

    private final StudyManagementService studyManagementService;

    @Retryable(
        value = {OptimisticLockException.class, ObjectOptimisticLockingFailureException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 50)
    )
    public void increaseMemberCount(Long studyId, Long userId) {
        studyManagementService.increaseMemberCount(studyId, userId);
    }

    @Retryable(
        value = {OptimisticLockException.class, ObjectOptimisticLockingFailureException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 50)
    )
    public void leaveStudyWithRetry(Long studyId, Long userId) {
        studyManagementService.leaveStudy(studyId, userId);
    }

    @Retryable(
        value = {OptimisticLockException.class, ObjectOptimisticLockingFailureException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 50)
    )
    public void deleteUserFromStudiesWithRetry(Long userId) {
        studyManagementService.deleteUserFromStudies(userId);
    }

    @Retryable(
        value = {OptimisticLockException.class, ObjectOptimisticLockingFailureException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 50)
    )
    public void registerExtensionParticipationWithRetry(Long studyId, Long userId) {
        studyManagementService.registerExtensionParticipation(studyId, userId);
    }

    @Recover
    public void recover(OptimisticLockException e, Long studyId, Long userId) {
        log.warn("스터디 currentCount 증가 실패 (studyId={}, userId={})", studyId, userId, e);
        throw new BusinessException(STUDY_MEMBER_COUNT_UPDATE_FAILED);
    }

    @Recover
    public void recover(ObjectOptimisticLockingFailureException e, Long studyId, Long userId) {
        log.warn("스터디 currentCount 증가 실패 (studyId={}, userId={})", studyId, userId, e);
        throw new BusinessException(STUDY_MEMBER_COUNT_UPDATE_FAILED);
    }
}
