package grep.neogul_coder.domain.study.service;

import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.study.enums.StudyMemberRole;
import grep.neogul_coder.domain.study.repository.StudyMemberRepository;
import grep.neogul_coder.domain.study.repository.StudyQueryRepository;
import grep.neogul_coder.domain.study.repository.StudyRepository;
import grep.neogul_coder.global.exception.business.BusinessException;
import grep.neogul_coder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static grep.neogul_coder.domain.study.enums.StudyMemberRole.LEADER;
import static grep.neogul_coder.domain.study.exception.code.StudyErrorCode.*;

@Transactional
@RequiredArgsConstructor
@Service
public class StudyManagementService {

    private final StudyRepository studyRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final StudyQueryRepository studyQueryRepository;

    public void leaveStudy(Long studyId, Long userId) {
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND));

        StudyMember studyMember = studyMemberRepository.findByStudyIdAndUserId(studyId, userId)
            .orElseThrow(() -> new NotFoundException(STUDY_NOT_MEMBER));

        validateStudyLeader(studyId, userId);
        studyMember.delete();
        study.decreaseMemberCount();
    }

    private void validateStudyLeader(Long studyId, Long userId) {
        StudyMemberRole role = studyQueryRepository.findMyRole(studyId, userId);
        if (role.equals(LEADER)) {
            throw new BusinessException(LEADER_CANNOT_LEAVE_STUDY);
        }
    }
}
