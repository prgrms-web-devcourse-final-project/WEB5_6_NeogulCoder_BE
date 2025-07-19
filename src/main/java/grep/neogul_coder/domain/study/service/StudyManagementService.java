package grep.neogul_coder.domain.study.service;

import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.study.controller.dto.request.ExtendStudyRequest;
import grep.neogul_coder.domain.study.controller.dto.response.ExtendParticipationResponse;
import grep.neogul_coder.domain.study.controller.dto.response.StudyExtensionResponse;
import grep.neogul_coder.domain.study.repository.StudyMemberQueryRepository;
import grep.neogul_coder.domain.study.repository.StudyMemberRepository;
import grep.neogul_coder.domain.study.repository.StudyRepository;
import grep.neogul_coder.global.exception.business.BusinessException;
import grep.neogul_coder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static grep.neogul_coder.domain.study.enums.StudyMemberRole.LEADER;
import static grep.neogul_coder.domain.study.exception.code.StudyErrorCode.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class StudyManagementService {

    private final StudyRepository studyRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final StudyMemberQueryRepository studyMemberQueryRepository;

    public StudyExtensionResponse getStudyExtension(Long studyId) {
        Study study = findValidStudy(studyId);

        List<ExtendParticipationResponse> members = studyMemberQueryRepository.findExtendParticipation(studyId);
        return StudyExtensionResponse.from(study, members);
    }

    public List<ExtendParticipationResponse> getExtendParticipations(Long studyId) {
        Study study = findValidStudy(studyId);

        return studyMemberQueryRepository.findExtendParticipation(studyId);
    }

    @Transactional
    public void leaveStudy(Long studyId, Long userId) {
        Study study = findValidStudy(studyId);

        StudyMember studyMember = findValidStudyMember(studyId, userId);

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

        StudyMember currentLeader = findValidStudyMember(studyId, userId);

        isLeader(currentLeader);

        StudyMember newLeader = findValidStudyMember(studyId, newLeaderId);

        currentLeader.changeRoleMember();
        newLeader.changeRoleLeader();
    }

    @Transactional
    public void deleteUserFromStudies(Long userId) {
        List<Study> studies = studyMemberRepository.findStudiesByUserId(userId);

        for (Study study : studies) {
            StudyMember studyMember = findValidStudyMember(study.getId(), userId);

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

    @Transactional
    public void extendStudy(Long studyId, ExtendStudyRequest request, Long userId) {
        Study originStudy = findValidStudy(studyId);

        StudyMember leader = findValidStudyMember(studyId, userId);
        isLeader(leader);

        validateStudyExtendable(originStudy, request.getNewEndDate());

        Study extendedStudy = request.toEntity(originStudy);
        studyRepository.save(extendedStudy);

        StudyMember extendedLeader = StudyMember.builder()
            .study(extendedStudy)
            .userId(userId)
            .role(LEADER)
            .build();
        studyMemberRepository.save(extendedLeader);
    }

    private Study findValidStudy(Long studyId) {
        return studyRepository.findById(studyId)
            .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND));
    }

    private StudyMember findValidStudyMember(Long studyId, Long userId) {
        return studyMemberRepository.findByStudyIdAndUserId(studyId, userId)
            .orElseThrow(() -> new NotFoundException(STUDY_MEMBER_NOT_FOUND));
    }

    private void randomDelegateLeader(Long studyId, StudyMember currentLeader) {

        isLeader(currentLeader);

        List<StudyMember> studyMembers = studyMemberRepository.findAvailableNewLeaders(studyId);

        if (studyMembers.isEmpty()) {
            throw new BusinessException(LEADER_CANNOT_LEAVE_STUDY);
        }

        StudyMember newLeader = studyMembers.get(new Random().nextInt(studyMembers.size()));

        currentLeader.changeRoleMember();
        newLeader.changeRoleLeader();
    }

    private void isLeader(StudyMember studyMember) {
        if (!studyMember.isLeader()) {
            throw new BusinessException(NOT_STUDY_LEADER);
        }
    }

    private void validateStudyExtendable(Study study, LocalDateTime endDate) {
        if (study.getEndDate().toLocalDate().isAfter(LocalDate.now().plusDays(7))) {
            throw new BusinessException(STUDY_EXTENSION_NOT_AVAILABLE);
        }

        if (endDate.toLocalDate().isBefore(study.getEndDate().toLocalDate())) {
            throw new BusinessException(END_DATE_BEFORE_ORIGIN_STUDY);
        }
    }
}
