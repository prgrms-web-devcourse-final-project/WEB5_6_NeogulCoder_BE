package grep.neogul_coder.domain.study.service;

import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.study.repository.StudyMemberRepository;
import grep.neogul_coder.domain.study.repository.StudyQueryRepository;
import grep.neogul_coder.domain.study.repository.StudyRepository;
import grep.neogul_coder.global.exception.business.BusinessException;
import grep.neogul_coder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            .orElseThrow(() -> new NotFoundException(STUDY_MEMBER_NOT_FOUND));

        validateIsLeader(studyMember);
        studyMember.delete();
        study.decreaseMemberCount();
    }

    public void delegateLeader(Long studyId, Long userId, Long newLeaderId) {
        if (userId.equals(newLeaderId)) {
            throw new BusinessException(LEADER_CANNOT_DELEGATE_TO_SELF);
        }

        StudyMember currentLeader = studyMemberRepository.findByStudyIdAndUserId(studyId, userId)
            .orElseThrow(() -> new NotFoundException(STUDY_MEMBER_NOT_FOUND));

        if (currentLeader.hasNotRoleLeader()) {
            throw new BusinessException(NOT_STUDY_LEADER);
        }

        StudyMember newLeader = studyMemberRepository.findByStudyIdAndUserId(studyId, newLeaderId)
            .orElseThrow(() -> new NotFoundException(STUDY_MEMBER_NOT_FOUND));

        currentLeader.changeRoleMember();
        newLeader.changeRoleLeader();
    }

    private void validateIsLeader(StudyMember studyMember) {
        if (studyMember.hasNotRoleLeader()) {
            throw new BusinessException(LEADER_CANNOT_LEAVE_STUDY);
        }
    }
}
