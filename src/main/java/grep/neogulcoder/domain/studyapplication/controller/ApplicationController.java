package grep.neogulcoder.domain.studyapplication.controller;

import grep.neogulcoder.domain.studyapplication.ApplicationStatus;
import grep.neogulcoder.domain.studyapplication.controller.dto.request.ApplicationCreateRequest;
import grep.neogulcoder.domain.studyapplication.controller.dto.response.MyApplicationPagingResponse;
import grep.neogulcoder.domain.studyapplication.controller.dto.response.ReceivedApplicationPagingResponse;
import grep.neogulcoder.domain.studyapplication.service.ApplicationService;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/recruitment-posts")
@RequiredArgsConstructor
@RestController
public class ApplicationController implements ApplicationSpecification {

    private final ApplicationService applicationService;

    @GetMapping("/{recruitment-post-id}/applications")
    public ApiResponse<ReceivedApplicationPagingResponse> getReceivedApplications(@PathVariable("recruitment-post-id") Long recruitmentPostId,
                                                                                  @PageableDefault(size = 5) Pageable pageable,
                                                                                  @AuthenticationPrincipal Principal userDetails) {
        return ApiResponse.success(applicationService.getReceivedApplicationsPaging(recruitmentPostId, pageable, userDetails.getUserId()));
    }

    @GetMapping("/applications")
    public ApiResponse<MyApplicationPagingResponse> getMyStudyApplications(@PageableDefault(size = 12) Pageable pageable,
                                                                           @RequestParam(required = false) ApplicationStatus status,
                                                                           @AuthenticationPrincipal Principal userDetails) {
        return ApiResponse.success(applicationService.getMyStudyApplicationsPaging(pageable, userDetails.getUserId(), status));
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
