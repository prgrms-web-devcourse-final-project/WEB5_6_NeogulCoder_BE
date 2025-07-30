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

import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.*;

@Service
@RequiredArgsConstructor
public class StudyManagementServiceFacade {

    private final StudyMemberCountService studyMemberCountService;

    public void increaseMemberCount(Long studyId, Long userId) {
        studyMemberCountService.increaseMemberCount(studyId, userId);
    }

    public void decreaseMemberCount(Long studyId, Long userId) {
        studyMemberCountService.decreaseMemberCount(studyId, userId);
    }
}
