package grep.neogulcoder.domain.studyapplication.service;

import grep.neogulcoder.domain.recruitment.post.RecruitmentPost;
import grep.neogulcoder.domain.recruitment.post.repository.RecruitmentPostRepository;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.enums.StudyMemberRole;
import grep.neogulcoder.domain.study.repository.StudyMemberRepository;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.domain.studyapplication.ApplicationStatus;
import grep.neogulcoder.domain.studyapplication.StudyApplication;
import grep.neogulcoder.domain.studyapplication.controller.dto.request.ApplicationCreateRequest;
import grep.neogulcoder.domain.studyapplication.controller.dto.response.MyApplicationPagingResponse;
import grep.neogulcoder.domain.studyapplication.controller.dto.response.MyApplicationResponse;
import grep.neogulcoder.domain.studyapplication.repository.ApplicationQueryRepository;
import grep.neogulcoder.domain.studyapplication.repository.ApplicationRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
import grep.neogulcoder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static grep.neogulcoder.domain.recruitment.RecruitmentErrorCode.NOT_FOUND;
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

    public MyApplicationPagingResponse getMyStudyApplicationsPaging(Pageable pageable, Long userId, ApplicationStatus status) {
        Page<MyApplicationResponse> page = applicationQueryRepository.findMyStudyApplicationsPaging(pageable, userId, status);
        return MyApplicationPagingResponse.of(page);
    }

    @Transactional
    public Long createApplication(Long recruitmentPostId, ApplicationCreateRequest request, Long userId) {
        RecruitmentPost recruitmentPost = findValidRecruimentPost(recruitmentPostId);

        validateNotLeaderApply(recruitmentPost, userId);
        validateNotAlreadyApplied(recruitmentPostId, userId);

        StudyApplication application = request.toEntity(recruitmentPostId, userId);
        applicationRepository.save(application);

        return application.getId();
    }

    @Transactional
    public void approveApplication(Long applicationId, Long userId) {
        StudyApplication application = findValidApplication(applicationId);
        RecruitmentPost post = findValidRecruimentPost(application.getRecruitmentPostId());
        Study study = findValidStudy(post);

        validateOnlyLeaderCanApprove(study, userId);
        validateStatusIsApplying(application);

        application.approve();

        StudyMember studyMember = StudyMember.createMember(study, application.getUserId());
        studyMemberRepository.save(studyMember);
        study.increaseMemberCount();
    }

    @Transactional
    public void rejectApplication(Long applicationId, Long userId) {
        StudyApplication application = findValidApplication(applicationId);
        RecruitmentPost post = findValidRecruimentPost(application.getRecruitmentPostId());
        Study study = findValidStudy(post);

        validateOnlyLeaderCanReject(study, userId);
        validateStatusIsApplying(application);

        application.reject();
    }

    private Study findValidStudy(RecruitmentPost post) {
        Study study = studyRepository.findByIdAndActivatedTrue(post.getStudyId())
            .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND));
        return study;
    }

    private StudyApplication findValidApplication(Long applicationId) {
        StudyApplication application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new NotFoundException(APPLICATION_NOT_FOUND));
        return application;
    }

    private RecruitmentPost findValidRecruimentPost(Long recruitmentPostId) {
        RecruitmentPost post = recruitmentPostRepository.findByIdAndActivatedTrue(recruitmentPostId)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));
        return post;
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
}
