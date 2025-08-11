package grep.neogulcoder.domain.study.service;

import grep.neogulcoder.domain.recruitment.RecruitmentPostStatus;
import grep.neogulcoder.domain.recruitment.post.repository.RecruitmentPostRepository;
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
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.*;

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
    private final RecruitmentPostRepository recruitmentPostRepository;

    public StudyExtensionResponse getStudyExtension(Long studyId) {
        Study study = getStudyById(studyId);

        List<ExtendParticipationResponse> members = studyMemberQueryRepository.findExtendParticipation(studyId);
        return StudyExtensionResponse.from(study, members);
    }

    public List<ExtendParticipationResponse> getExtendParticipations(Long studyId) {
        getStudyById(studyId);

        return studyMemberQueryRepository.findExtendParticipation(studyId);
    }

    @Transactional
    public void leaveStudy(Long studyId, Long userId) {
        Study study = getStudyById(studyId);
        StudyMember studyMember = getStudyMemberById(studyId, userId);

        if (isLastMember(study)) {
            study.delete();
            studyMember.delete();
            completeRecruitmentPostIfExists(studyId);
            return;
        }

        if (studyMember.isLeader()) {
            randomDelegateLeader(studyId, studyMember);
        }

        studyMember.delete();
        studyManagementServiceFacade.decreaseMemberCount(studyId, userId);
    }

    @Transactional
    public void delegateLeader(Long studyId, Long userId, Long newLeaderId) {
        if (userId.equals(newLeaderId)) {
            throw new BusinessException(LEADER_CANNOT_DELEGATE_TO_SELF);
        }

        StudyMember currentLeader = getStudyMemberById(studyId, userId);
        validateLeader(currentLeader);

        StudyMember newLeader = getStudyMemberById(studyId, newLeaderId);

        currentLeader.changeRoleMember();
        newLeader.changeRoleLeader();
    }

    @Transactional
    public void deleteUserFromStudies(Long userId) {
        List<StudyMember> myStudyMembers = studyMemberQueryRepository.findActivatedStudyMembersWithStudy(userId);
        Map<Long, List<StudyMember>> activatedMemberMap = getActivatedMemberMap(myStudyMembers);

        for (StudyMember myMember : myStudyMembers) {
            Study study = myMember.getStudy();
            List<StudyMember> activatedMembers = activatedMemberMap.getOrDefault(study.getId(), List.of());

            if (isLastActivatedMember(activatedMembers)) {
                study.delete();
                myMember.delete();
                continue;
            }

            if (myMember.isLeader()) {
                randomDelegateLeader(study.getId(), myMember);
            }

            myMember.delete();
            studyManagementServiceFacade.decreaseMemberCount(study.getId(), userId);
        }
    }

    @Transactional
    public Long extendStudy(Long studyId, ExtendStudyRequest request, Long userId) {
        Study originStudy = getStudyById(studyId);

        StudyMember leader = getStudyMemberById(studyId, userId);
        validateLeader(leader);

        validateStudyExtendable(originStudy, request.getNewEndDate());

        Study extendedStudy = request.toEntity(originStudy, userId);
        studyRepository.save(extendedStudy);
        originStudy.extend();
        leader.participate();

        StudyMember extendedLeader = StudyMember.createLeader(extendedStudy, userId);
        studyMemberRepository.save(extendedLeader);

        eventPublisher.publishEvent(new StudyExtendEvent(originStudy.getId()));

        return extendedStudy.getId();
    }

    @Transactional
    public void registerExtensionParticipation(Long studyId, Long userId) {
        getStudyById(studyId);
        StudyMember studyMember = getStudyMemberById(studyId, userId);

        if (studyMember.isParticipated()) {
            throw new BusinessException(ALREADY_REGISTERED_PARTICIPATION);
        }

        studyMember.participate();

        Study extendedStudy = studyRepository.findByOriginStudyIdAndActivatedTrue(studyId)
            .orElseThrow(() -> new BusinessException(EXTENDED_STUDY_NOT_FOUND));

        StudyMember extendMember = StudyMember.createMember(extendedStudy, userId);
        studyMemberRepository.save(extendMember);
        studyManagementServiceFacade.increaseMemberCount(extendedStudy.getId(), userId);
    }

    @Transactional
    public void inviteTargetUser(Long studyId, Long userId, String targetUserNickname) {
        StudyMember studyMember = getStudyMemberById(studyId, userId);
        studyMember.isLeader();

        User targetUser = userRepository.findByNickname(targetUserNickname)
            .orElseThrow(() -> new NotFoundException(
                UserErrorCode.USER_NOT_FOUND));

        eventPublisher.publishEvent(new StudyInviteEvent(studyId, userId, targetUser.getId()));
    }

    @Transactional
    public void reactiveStudy(Long studyId) {
        Study study = getStudyById(studyId);
        study.reactive();
    }

    private Study getStudyById(Long studyId) {
        return studyRepository.findById(studyId)
            .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND));
    }

    private StudyMember getStudyMemberById(Long studyId, Long userId) {
        return studyMemberRepository.findByStudyIdAndUserIdAndActivatedTrue(studyId, userId)
            .orElseThrow(() -> new NotFoundException(STUDY_MEMBER_NOT_FOUND));
    }

    private boolean isLastMember(Study study) {
        int activatedMemberCount = studyMemberRepository.countByStudyIdAndActivatedTrue(
            study.getId());
        return activatedMemberCount == 1;
    }

    private void completeRecruitmentPostIfExists(Long studyId) {
        recruitmentPostRepository.findByStudyIdAndActivatedTrue(studyId)
            .ifPresent(recruitmentPost -> recruitmentPost.updateStatus(RecruitmentPostStatus.COMPLETE));
    }

    private void randomDelegateLeader(Long studyId, StudyMember currentLeader) {
        validateLeader(currentLeader);

        List<StudyMember> studyMembers = studyMemberRepository.findAvailableNewLeaders(studyId);

        if (studyMembers.isEmpty()) {
            throw new BusinessException(LEADER_CANNOT_LEAVE_STUDY);
        }

        StudyMember newLeader = studyMembers.get(new Random().nextInt(studyMembers.size()));

        currentLeader.changeRoleMember();
        newLeader.changeRoleLeader();
    }

    private void validateLeader(StudyMember studyMember) {
        if (!studyMember.isLeader()) {
            throw new BusinessException(NOT_STUDY_LEADER);
        }
    }

    private Map<Long, List<StudyMember>> getActivatedMemberMap(List<StudyMember> myStudyMembers) {
        List<Long> studyIds = myStudyMembers.stream()
            .map(sm -> sm.getStudy().getId())
            .distinct()
            .toList();

        List<StudyMember> allActivatedMembers = studyMemberQueryRepository.findActivatedMembersByStudyIds(studyIds);

        return allActivatedMembers.stream()
            .collect(Collectors.groupingBy(studyMember -> studyMember.getStudy().getId()));
    }

    private static boolean isLastActivatedMember(List<StudyMember> activatedMembers) {
        return activatedMembers.size() == 1;
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
