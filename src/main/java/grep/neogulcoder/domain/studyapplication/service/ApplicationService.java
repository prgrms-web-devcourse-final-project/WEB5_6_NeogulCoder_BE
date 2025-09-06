package grep.neogulcoder.domain.studyapplication.service;

import grep.neogulcoder.domain.alram.type.AlarmType;
import grep.neogulcoder.domain.recruitment.post.RecruitmentPost;
import grep.neogulcoder.domain.recruitment.post.repository.RecruitmentPostRepository;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.enums.StudyMemberRole;
import grep.neogulcoder.domain.study.repository.StudyMemberQueryRepository;
import grep.neogulcoder.domain.study.repository.StudyMemberRepository;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.domain.study.service.StudyManagementServiceFacade;
import grep.neogulcoder.domain.studyapplication.ApplicationStatus;
import grep.neogulcoder.domain.studyapplication.StudyApplication;
import grep.neogulcoder.domain.studyapplication.controller.dto.request.ApplicationCreateRequest;
import grep.neogulcoder.domain.studyapplication.controller.dto.response.MyApplicationPagingResponse;
import grep.neogulcoder.domain.studyapplication.controller.dto.response.MyApplicationResponse;
import grep.neogulcoder.domain.studyapplication.controller.dto.response.ReceivedApplicationPagingResponse;
import grep.neogulcoder.domain.studyapplication.controller.dto.response.ReceivedApplicationResponse;
import grep.neogulcoder.domain.studyapplication.event.ApplicationEvent;
import grep.neogulcoder.domain.studyapplication.event.ApplicationStatusChangedEvent;
import grep.neogulcoder.domain.studyapplication.repository.ApplicationQueryRepository;
import grep.neogulcoder.domain.studyapplication.repository.ApplicationRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
import grep.neogulcoder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static grep.neogulcoder.domain.recruitment.RecruitmentErrorCode.NOT_FOUND;
import static grep.neogulcoder.domain.recruitment.RecruitmentErrorCode.NOT_OWNER;
import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.STUDY_NOT_FOUND;
import static grep.neogulcoder.domain.studyapplication.exception.code.ApplicationErrorCode.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationQueryRepository applicationQueryRepository;
    private final RecruitmentPostRepository recruitmentPostRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final StudyRepository studyRepository;
    private final StudyMemberQueryRepository studyMemberQueryRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final StudyManagementServiceFacade studyManagementServiceFacade;

    @Transactional
    public ReceivedApplicationPagingResponse getReceivedApplicationsPaging(Long recruitmentPostId, Pageable pageable, Long userId) {
        RecruitmentPost recruitmentPost = getRecruitmentPostById(recruitmentPostId);

        validateOwner(userId, recruitmentPost);
        applicationRepository.markAllAsReadByRecruitmentPostId(recruitmentPostId);

        Page<ReceivedApplicationResponse> page = applicationQueryRepository.findReceivedApplicationsPaging(recruitmentPostId, pageable);
        return ReceivedApplicationPagingResponse.of(page);
    }

    public MyApplicationPagingResponse getMyStudyApplicationsPaging(Pageable pageable, Long userId, ApplicationStatus status) {
        Page<MyApplicationResponse> page = applicationQueryRepository.findMyStudyApplicationsPaging(pageable, userId, status);
        return MyApplicationPagingResponse.of(page);
    }

    @Transactional
    public Long createApplication(Long recruitmentPostId, ApplicationCreateRequest request, Long userId) {
        RecruitmentPost recruitmentPost = getRecruitmentPostById(recruitmentPostId);

        validateNotLeaderApply(recruitmentPost, userId);
        validateNotAlreadyApplied(recruitmentPostId, userId);
        validateApplicantStudyLimit(userId);

        StudyApplication application = request.toEntity(recruitmentPostId, userId);
        applicationRepository.save(application);

        eventPublisher.publishEvent(new ApplicationEvent(recruitmentPostId, application.getId()));

        return application.getId();
    }

    @Transactional
    public void approveApplication(Long applicationId, Long userId) {
        StudyApplication application = getApplicationById(applicationId);
        RecruitmentPost post = getRecruitmentPostById(application.getRecruitmentPostId());
        Study study = getStudyByRecruitmentPostId(post);

        validateOnlyLeaderCanApprove(study, userId);
        validateStatusIsApplying(application);
        validateParticipantStudyLimit(application.getUserId());

        application.approve();

        StudyMember studyMember = StudyMember.createMember(study, application.getUserId());
        studyMemberRepository.save(studyMember);
        studyManagementServiceFacade.increaseMemberCount(study.getId(), application.getUserId());

        eventPublisher.publishEvent(new ApplicationStatusChangedEvent(applicationId, AlarmType.STUDY_APPLICATION_APPROVED));
    }

    @Transactional
    public void rejectApplication(Long applicationId, Long userId) {
        StudyApplication application = getApplicationById(applicationId);
        RecruitmentPost post = getRecruitmentPostById(application.getRecruitmentPostId());
        Study study = getStudyByRecruitmentPostId(post);

        validateOnlyLeaderCanReject(study, userId);
        validateStatusIsApplying(application);

        application.reject();

        eventPublisher.publishEvent(new ApplicationStatusChangedEvent(applicationId, AlarmType.STUDY_APPLICATION_REJECTED));
    }

    private Study getStudyByRecruitmentPostId(RecruitmentPost post) {
        return studyRepository.findByIdAndActivatedTrue(post.getStudyId())
            .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND));
    }

    private StudyApplication getApplicationById(Long applicationId) {
        return applicationRepository.findById(applicationId)
            .orElseThrow(() -> new NotFoundException(APPLICATION_NOT_FOUND));
    }

    private RecruitmentPost getRecruitmentPostById(Long recruitmentPostId) {
        return recruitmentPostRepository.findByIdAndActivatedTrue(recruitmentPostId)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));
    }

    private static void validateOwner(Long userId, RecruitmentPost recruitmentPost) {
        if (recruitmentPost.isNotOwnedBy(userId)) {
            throw new BusinessException(NOT_OWNER);
        }
    }

    private void validateNotLeaderApply(RecruitmentPost recruitmentPost, Long userId) {
        boolean isLeader = studyMemberRepository.existsByStudyIdAndUserIdAndRole(recruitmentPost.getStudyId(), userId, StudyMemberRole.LEADER);
        if (isLeader) {
            throw new BusinessException(LEADER_CANNOT_APPLY);
        }
    }

    private void validateNotAlreadyApplied(Long recruitmentPostId, Long userId) {
        boolean alreadyApplied = applicationRepository.existsByRecruitmentPostIdAndUserId(recruitmentPostId, userId);
        if (alreadyApplied) {
            throw new BusinessException(ALREADY_APPLICATION);
        }
    }

    private static void validateStatusIsApplying(StudyApplication application) {
        if (application.getStatus() != ApplicationStatus.APPLYING) {
            throw new BusinessException(APPLICATION_NOT_APPLYING);
        }
    }

    private void validateOnlyLeaderCanApprove(Study study, Long userId) {
        boolean isLeader = studyMemberRepository.existsByStudyIdAndUserIdAndRole(study.getId(), userId, StudyMemberRole.LEADER);
        if (!isLeader) {
            throw new BusinessException(LEADER_ONLY_APPROVED);
        }
    }

    private void validateOnlyLeaderCanReject(Study study, Long userId) {
        boolean isLeader = studyMemberRepository.existsByStudyIdAndUserIdAndRole(study.getId(), userId, StudyMemberRole.LEADER);
        if (!isLeader) {
            throw new BusinessException(LEADER_ONLY_REJECTED);
        }
    }

    private void validateApplicantStudyLimit(Long userId) {
        int joinedStudyCount = studyMemberQueryRepository.countActiveUnfinishedStudies(userId);
        if (Study.isOverJoinLimit(joinedStudyCount)) {
            throw new BusinessException(APPLICATION_PARTICIPATION_LIMIT_EXCEEDED);
        }
    }

    private void validateParticipantStudyLimit(Long userId) {
        int joinedStudyCount = studyMemberQueryRepository.countActiveUnfinishedStudies(userId);
        if (Study.isOverJoinLimit(joinedStudyCount)) {
            throw new BusinessException(APPLICATION_PARTICIPANT_LIMIT_EXCEEDED);
        }
    }
}
