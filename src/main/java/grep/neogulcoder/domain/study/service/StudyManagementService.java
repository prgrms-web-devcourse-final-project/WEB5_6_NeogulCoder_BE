package grep.neogulcoder.domain.study.service;

import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.controller.dto.request.ExtendStudyRequest;
import grep.neogulcoder.domain.study.controller.dto.response.ExtendParticipationResponse;
import grep.neogulcoder.domain.study.controller.dto.response.StudyExtensionResponse;
import grep.neogulcoder.domain.study.repository.StudyMemberQueryRepository;
import grep.neogulcoder.domain.study.repository.StudyMemberRepository;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
import grep.neogulcoder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static grep.neogulcoder.domain.study.enums.StudyMemberRole.LEADER;
import static grep.neogulcoder.domain.study.enums.StudyMemberRole.MEMBER;
import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.*;

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

        if (isLastMember(study)) {
            study.delete();
            studyMember.delete();
            return;
        }

        if (studyMember.isLeader()) {
            randomDelegateLeader(studyId, studyMember);
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

            if (isLastMember(study)) {
                study.delete();
                studyMember.delete();
                continue;
            }

            if (studyMember.isLeader()) {
                randomDelegateLeader(study.getId(), studyMember);
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
        originStudy.extend();
        leader.participate();

        StudyMember extendedLeader = StudyMember.builder()
            .study(extendedStudy)
            .userId(userId)
            .role(LEADER)
            .build();
        studyMemberRepository.save(extendedLeader);
    }

    @Transactional
    public void registerExtensionParticipation(Long studyId, Long userId) {
        Study originStudy = findValidStudy(studyId);
        StudyMember studyMember = findValidStudyMember(studyId, userId);

        if (studyMember.isParticipated()) {
            throw new BusinessException(ALREADY_REGISTERED_PARTICIPATION);
        }

        studyMember.participate();

        Study extendedStudy = studyRepository.findByOriginStudyIdAndActivatedTrue(studyId)
            .orElseThrow(() -> new BusinessException(EXTENDED_STUDY_NOT_FOUND));

        StudyMember extendMember = StudyMember.builder()
            .study(extendedStudy)
            .userId(userId)
            .role(MEMBER)
            .build();
        studyMemberRepository.save(extendMember);
    }

    private Study findValidStudy(Long studyId) {
        return studyRepository.findById(studyId)
            .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND));
    }

    private StudyMember findValidStudyMember(Long studyId, Long userId) {
        return studyMemberRepository.findByStudyIdAndUserId(studyId, userId)
            .orElseThrow(() -> new NotFoundException(STUDY_MEMBER_NOT_FOUND));
    }

    private boolean isLastMember(Study study) {
        int activatedMemberCount = studyMemberRepository.countByStudyIdAndActivatedTrue(study.getId());
        return activatedMemberCount == 1;
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
        if (study.alreadyExtended()) {
            throw new BusinessException(ALREADY_EXTENDED_STUDY);
        }

        if (study.getEndDate().toLocalDate().isAfter(LocalDate.now().plusDays(7))) {
            throw new BusinessException(STUDY_EXTENSION_NOT_AVAILABLE);
        }

        if (endDate.toLocalDate().isBefore(study.getEndDate().toLocalDate())) {
            throw new BusinessException(END_DATE_BEFORE_ORIGIN_STUDY);
        }
    }
}
