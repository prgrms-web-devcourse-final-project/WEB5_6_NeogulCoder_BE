package grep.neogulcoder.domain.study.service;

import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.STUDY_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudyMemberCountService {

    private final StudyRepository studyRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void increaseMemberCount(Long studyId) {
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND));
        study.increaseMemberCount();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void decreaseMemberCount(Long studyId) {
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND));
        study.decreaseMemberCount();
    }
}
