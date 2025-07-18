package grep.neogul_coder.domain.study.service;

import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.study.repository.StudyMemberRepository;
import grep.neogul_coder.domain.study.repository.StudyRepository;
import grep.neogul_coder.global.exception.business.BusinessException;
import grep.neogul_coder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

import static grep.neogul_coder.domain.study.exception.code.StudyErrorCode.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class StudyManagementService {

    private final StudyRepository studyRepository;
    private final StudyMemberRepository studyMemberRepository;

    @Transactional
    public void leaveStudy(Long studyId, Long userId) {
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND));

        StudyMember studyMember = studyMemberRepository.findByStudyIdAndUserId(studyId, userId)
            .orElseThrow(() -> new NotFoundException(STUDY_MEMBER_NOT_FOUND));

        if (studyMember.isLeader()) {
            int activatedMemberCount = studyMemberRepository.countByStudyIdAndActivatedTrue(studyId);

            if (activatedMemberCount == 1) {
                study.delete();
                studyMember.delete();
                return;
            } else {
                randomDelegateLeader(studyId, studyMember);
            }
        }

        studyMember.delete();
        study.decreaseMemberCount();
    }

    @Transactional
    public void delegateLeader(Long studyId, Long userId, Long newLeaderId) {
        if (userId.equals(newLeaderId)) {
            throw new BusinessException(LEADER_CANNOT_DELEGATE_TO_SELF);
        }

        StudyMember currentLeader = studyMemberRepository.findByStudyIdAndUserId(studyId, userId)
            .orElseThrow(() -> new NotFoundException(STUDY_MEMBER_NOT_FOUND));

        if (!currentLeader.isLeader()) {
            throw new BusinessException(NOT_STUDY_LEADER);
        }

        StudyMember newLeader = studyMemberRepository.findByStudyIdAndUserId(studyId, newLeaderId)
            .orElseThrow(() -> new NotFoundException(STUDY_MEMBER_NOT_FOUND));

        currentLeader.changeRoleMember();
        newLeader.changeRoleLeader();
    }

    @Transactional
    public void deleteUserFromStudies(Long userId) {
        List<Study> studies = studyMemberRepository.findStudiesByUserId(userId);

        for (Study study : studies) {
            StudyMember studyMember = studyMemberRepository.findByStudyIdAndUserId(study.getId(), userId)
                .orElseThrow(() -> new NotFoundException(STUDY_MEMBER_NOT_FOUND));

            boolean isLeader = studyMember.isLeader();
            int activatedMemberCount = studyMemberRepository.countByStudyIdAndActivatedTrue(study.getId());

            if (isLeader) {
                if (activatedMemberCount == 1) {
                    study.delete();
                    studyMember.delete();
                    continue;
                } else {
                    randomDelegateLeader(study.getId(), studyMember);
                }
            }

            studyMember.delete();
            study.decreaseMemberCount();
        }
    }

    private void randomDelegateLeader(Long studyId, StudyMember currentLeader) {

        if (!currentLeader.isLeader()) {
            throw new BusinessException(NOT_STUDY_LEADER);
        }

        List<StudyMember> studyMembers = studyMemberRepository.findAvailableNewLeaders(studyId);

        if (studyMembers.isEmpty()) {
            throw new BusinessException(LEADER_CANNOT_LEAVE_STUDY);
        }

        StudyMember newLeader = studyMembers.get(new Random().nextInt(studyMembers.size()));

        currentLeader.changeRoleMember();
        newLeader.changeRoleLeader();
    }
}
