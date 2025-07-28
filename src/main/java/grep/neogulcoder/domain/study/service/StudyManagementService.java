package grep.neogulcoder.domain.study.service;

import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.controller.dto.request.ExtendStudyRequest;
import grep.neogulcoder.domain.study.controller.dto.response.ExtendParticipationResponse;
import grep.neogulcoder.domain.study.controller.dto.response.StudyExtensionResponse;
import grep.neogulcoder.domain.study.event.StudyExtendEvent;
import grep.neogulcoder.domain.study.event.StudyInviteEvent;
import grep.neogulcoder.domain.study.repository.StudyMemberQueryRepository;
import grep.neogulcoder.domain.study.repository.StudyMemberRepository;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.domain.users.entity.User;
import grep.neogulcoder.domain.users.exception.code.UserErrorCode;
import grep.neogulcoder.domain.users.repository.UserRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
import grep.neogulcoder.global.exception.business.NotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static grep.neogulcoder.domain.study.enums.StudyMemberRole.LEADER;
import static grep.neogulcoder.domain.study.enums.StudyMemberRole.MEMBER;
import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.ALREADY_EXTENDED_STUDY;
import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.ALREADY_REGISTERED_PARTICIPATION;
import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.END_DATE_BEFORE_ORIGIN_STUDY;
import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.EXTENDED_STUDY_NOT_FOUND;
import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.LEADER_CANNOT_DELEGATE_TO_SELF;
import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.LEADER_CANNOT_LEAVE_STUDY;
import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.NOT_STUDY_LEADER;
import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.STUDY_EXTENSION_NOT_AVAILABLE;
import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.STUDY_MEMBER_NOT_FOUND;
import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.STUDY_NOT_FOUND;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class StudyManagementService {

    private final StudyRepository studyRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final StudyMemberQueryRepository studyMemberQueryRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final StudyManagementServiceFacade studyManagementServiceFacade;

    public StudyExtensionResponse getStudyExtension(Long studyId) {
        Study study = findValidStudy(studyId);

        List<ExtendParticipationResponse> members = studyMemberQueryRepository.findExtendParticipation(
            studyId);
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
        studyManagementServiceFacade.decreaseMemberCount(study, userId);
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
        List<StudyMember> myStudyMembers = studyMemberQueryRepository.findActivatedStudyMembersWithStudy(userId);

        List<Long> studyIds = myStudyMembers.stream()
            .map(studyMember -> studyMember.getStudy().getId())
            .distinct()
            .toList();

        List<StudyMember> allActivatedMembers = studyMemberQueryRepository.findActivatedMembersByStudyIds(studyIds);

        Map<Long, List<StudyMember>> activatedMemberMap = allActivatedMembers.stream()
            .collect(Collectors.groupingBy(studyMember -> studyMember.getStudy().getId()));

        for (StudyMember myMember : myStudyMembers) {
            Study study = myMember.getStudy();
            List<StudyMember> activatedMembers = activatedMemberMap.getOrDefault(study.getId(), List.of());

            if (activatedMembers.size() == 1) {
                study.delete();
                myMember.delete();
                continue;
            }

            if (myMember.isLeader()) {
                randomDelegateLeader(study.getId(), myMember);
            }

            myMember.delete();
            studyManagementServiceFacade.decreaseMemberCount(study, userId);
        }
    }

    @Transactional
    public Long extendStudy(Long studyId, ExtendStudyRequest request, Long userId) {
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

        eventPublisher.publishEvent(new StudyExtendEvent(originStudy.getId()));

        return extendedStudy.getId();
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

    @Transactional
    public void inviteTargetUser(Long studyId, Long userId, String targetUserNickname) {
        StudyMember studyMember = findValidStudyMember(studyId, userId);
        studyMember.isLeader();

        User targetUser = userRepository.findByNickname(targetUserNickname)
            .orElseThrow(() -> new NotFoundException(
                UserErrorCode.USER_NOT_FOUND));

        eventPublisher.publishEvent(new StudyInviteEvent(studyId, userId, targetUser.getId()));
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
        int activatedMemberCount = studyMemberRepository.countByStudyIdAndActivatedTrue(
            study.getId());
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
