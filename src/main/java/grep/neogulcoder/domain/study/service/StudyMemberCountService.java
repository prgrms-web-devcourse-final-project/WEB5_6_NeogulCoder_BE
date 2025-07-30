package grep.neogulcoder.domain.study.service;

import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
import grep.neogulcoder.global.exception.business.NotFoundException;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.STUDY_MEMBER_COUNT_UPDATE_FAILED;
import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.STUDY_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudyMemberCountService {

    private final StudyRepository studyRepository;
    private static final int MAX_RETRY = 3;

    @Transactional
    public void increaseMemberCount(Long studyId, Long userId) {
        int retry = 0;
        while (retry < MAX_RETRY) {
            try {
                Study study = getStudyById(studyId);
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

        log.warn("스터디 currentCount 증가 실패 (studyId={}, userId={})", studyId, userId);
        throw new BusinessException(STUDY_MEMBER_COUNT_UPDATE_FAILED);
    }

    @Transactional
    public void decreaseMemberCount(Long studyId, Long userId) {
        int retry = 0;
        while (retry < MAX_RETRY) {
            try {
                Study study = getStudyById(studyId);
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

        log.warn("스터디 currentCount 감소 실패 (studyId={}, userId={})", studyId, userId);
        throw new BusinessException(STUDY_MEMBER_COUNT_UPDATE_FAILED);
    }

    private Study getStudyById(Long studyId) {
        return studyRepository.findById(studyId)
            .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND));
    }
}
