package grep.neogul_coder.domain.studyapplication.service;

import grep.neogul_coder.domain.recruitment.post.RecruitmentPost;
import grep.neogul_coder.domain.recruitment.post.repository.RecruitmentPostRepository;
import grep.neogul_coder.domain.study.enums.StudyMemberRole;
import grep.neogul_coder.domain.study.repository.StudyMemberRepository;
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
import static grep.neogul_coder.domain.studyapplication.exception.code.ApplicationErrorCode.ALREADY_APPLICATION;
import static grep.neogul_coder.domain.studyapplication.exception.code.ApplicationErrorCode.LEADER_CANNOT_APPLY;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationQueryRepository applicationQueryRepository;
    private final RecruitmentPostRepository recruitmentPostRepository;
    private final StudyMemberRepository studyMemberRepository;

    public List<MyApplicationResponse> getMyStudyApplications(Long userId) {
        return applicationQueryRepository.findMyApplications(userId);
    }

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
