package grep.neogul_coder.domain.studyapplication.service;

import grep.neogul_coder.domain.recruitment.post.RecruitmentPost;
import grep.neogul_coder.domain.recruitment.post.repository.RecruitmentPostRepository;
import grep.neogul_coder.domain.study.enums.StudyMemberRole;
import grep.neogul_coder.domain.study.repository.StudyMemberRepository;
import grep.neogul_coder.domain.studyapplication.StudyApplication;
import grep.neogul_coder.domain.studyapplication.controller.dto.request.ApplicationCreateRequest;
import grep.neogul_coder.domain.studyapplication.repository.ApplicationRepository;
import grep.neogul_coder.global.exception.business.BusinessException;
import grep.neogul_coder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static grep.neogul_coder.domain.recruitment.RecruitmentErrorCode.*;
import static grep.neogul_coder.domain.studyapplication.exception.code.ApplicationErrorCode.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final RecruitmentPostRepository recruitmentPostRepository;
    private final StudyMemberRepository studyMemberRepository;

    @Transactional
    public Long createApplication(ApplicationCreateRequest request) {
        RecruitmentPost recruitmentPost = recruitmentPostRepository.findByIdAndActivatedTrue(request.getRecruitmentPostId())
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));

        validateNotLeader(request, recruitmentPost);
        validateNotAlreadyApplied(request);

        StudyApplication application = request.toEntity();
        applicationRepository.save(application);

        return application.getId();
    }

    private void validateNotLeader(ApplicationCreateRequest request, RecruitmentPost recruitmentPost) {
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
}
