package grep.neogul_coder.domain.studyapplication.service;

import grep.neogul_coder.domain.recruitment.post.RecruitmentPost;
import grep.neogul_coder.domain.recruitment.post.repository.RecruitmentPostRepository;
import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.study.enums.StudyMemberRole;
import grep.neogul_coder.domain.study.repository.StudyMemberRepository;
import grep.neogul_coder.domain.study.repository.StudyRepository;
import grep.neogul_coder.domain.studyapplication.ApplicationStatus;
import grep.neogul_coder.domain.studyapplication.StudyApplication;
import grep.neogul_coder.domain.studyapplication.controller.dto.request.ApplicationCreateRequest;
import grep.neogul_coder.domain.studyapplication.controller.dto.response.MyApplicationResponse;
import grep.neogul_coder.domain.studyapplication.repository.ApplicationQueryRepository;
import grep.neogul_coder.domain.studyapplication.repository.ApplicationRepository;
import grep.neogul_coder.global.exception.business.BusinessException;
import grep.neogul_coder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static grep.neogul_coder.domain.recruitment.RecruitmentErrorCode.NOT_FOUND;
import static grep.neogul_coder.domain.study.exception.code.StudyErrorCode.STUDY_NOT_FOUND;
import static grep.neogul_coder.domain.studyapplication.exception.code.ApplicationErrorCode.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationQueryRepository applicationQueryRepository;
    private final RecruitmentPostRepository recruitmentPostRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final StudyRepository studyRepository;

    public List<MyApplicationResponse> getMyStudyApplications(Long userId) {
        return applicationQueryRepository.findMyApplications(userId);
    }

    @Transactional
    public Long createApplication(ApplicationCreateRequest request) {
        RecruitmentPost recruitmentPost = findValidRecruimentPost(request.getRecruitmentPostId());

        validateNotLeaderApply(request, recruitmentPost);
        validateNotAlreadyApplied(request);

        StudyApplication application = request.toEntity();
        applicationRepository.save(application);

        return application.getId();
    }

    @Transactional
    public void approveApplication(Long applicationId, Long userId) {
        StudyApplication application = findValidApplication(applicationId);
        RecruitmentPost post = findValidRecruimentPost(application.getRecruitmentPostId());
        Study study = findValidStudy(post);

        validateOnlyLeaderCanApproved(study, userId);
        validateAlreadyApproved(application);

        application.approve();

        StudyMember studyMember = StudyMember.createMember(study, application.getUserId());
        studyMemberRepository.save(studyMember);

        study.increaseMemberCount();
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

    private RecruitmentPost findValidRecruimentPost(Long applicationId) {
        RecruitmentPost post = recruitmentPostRepository.findByIdAndActivatedTrue(applicationId)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));
        return post;
    }

    private void validateNotLeaderApply(ApplicationCreateRequest request, RecruitmentPost recruitmentPost) {
        boolean isLeader = studyMemberRepository.existsByStudyIdAndUserIdAndRole(recruitmentPost.getStudyId(), request.getUserId(), StudyMemberRole.LEADER);
        if (isLeader) {
            throw new BusinessException(LEADER_CANNOT_APPLY);
        }
    }

    private void validateNotAlreadyApplied(ApplicationCreateRequest request) {
        boolean alreadyApplied = applicationRepository.existsByRecruitmentPostIdAndUserId(request.getRecruitmentPostId(), request.getUserId());
        if (alreadyApplied) {
            throw new BusinessException(ALREADY_APPLICATION);
        }
    }

    private static void validateAlreadyApproved(StudyApplication application) {
        if (application.getStatus() == ApplicationStatus.APPROVED) {
            throw new BusinessException(ALREADY_APPROVED);
        }
    }

    private void validateOnlyLeaderCanApproved(Study study, Long userId) {
        boolean isLeader = studyMemberRepository.existsByStudyIdAndUserIdAndRole(study.getId(), userId, StudyMemberRole.LEADER);
        if (!isLeader) {
            throw new BusinessException(LEADER_ONLY_APPROVED);
        }
    }
}
