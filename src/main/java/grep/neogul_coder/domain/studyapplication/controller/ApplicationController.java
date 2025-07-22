package grep.neogul_coder.domain.studyapplication.controller;

import grep.neogul_coder.domain.studyapplication.controller.dto.request.ApplicationCreateRequest;
import grep.neogul_coder.domain.studyapplication.controller.dto.response.MyApplicationResponse;
import grep.neogul_coder.domain.studyapplication.service.ApplicationService;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/recruitment-posts")
@RequiredArgsConstructor
@RestController
public class ApplicationController implements ApplicationSpecification {

    private final ApplicationService applicationService;

    @GetMapping("/{recruitment-post-id}/applications")
    public ApiResponse<List<MyApplicationResponse>> getMyStudyApplications(@PathVariable("recruitment-post-id") Long recruitmentPostId,
                                                                           @AuthenticationPrincipal Principal userDetails) {
        return ApiResponse.success(applicationService.getMyStudyApplications(userDetails.getUserId()));
    }

    @PostMapping("/{recruitment-post-id}/applications")
    public ApiResponse<Long> createApplication(@PathVariable("recruitment-post-id") Long recruitmentPostId,
                                               @RequestBody @Valid ApplicationCreateRequest request,
                                               @AuthenticationPrincipal Principal userDetails) {
        Long id = applicationService.createApplication(recruitmentPostId, request, userDetails.getUserId());
        return ApiResponse.success(id);
    }

    @PostMapping("/applications/{applicationId}/approve")
    public ApiResponse<Void> approveApplication(@PathVariable("applicationId") Long applicationId,
                                                @AuthenticationPrincipal Principal userDetails) {
        applicationService.approveApplication(applicationId, userDetails.getUserId());
        return ApiResponse.noContent();
    }

    @PostMapping("/applications/{applicationId}/reject")
    public ApiResponse<Void> rejectApplication(@PathVariable("applicationId") Long applicationId,
                                               @AuthenticationPrincipal Principal userDetails) {
        applicationService.rejectApplication(applicationId, userDetails.getUserId());
        return ApiResponse.noContent();
    }
}
