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

@RequestMapping("/api/applications")
@RequiredArgsConstructor
@RestController
public class ApplicationController implements ApplicationSpecification {

    private final ApplicationService applicationService;

    @GetMapping
    public ApiResponse<List<MyApplicationResponse>> getMyStudyApplications(@AuthenticationPrincipal Principal userDetails) {
        return ApiResponse.success(applicationService.getMyStudyApplications(userDetails.getUserId()));
    }

    @PostMapping
    public ApiResponse<Long> createApplication(@RequestBody @Valid ApplicationCreateRequest request) {
        Long id = applicationService.createApplication(request);
        return ApiResponse.success(id);
    }

    @PostMapping("/{applicationId}/approve")
    public ApiResponse<Void> approveApplication(@PathVariable("applicationId") Long applicationId,
                                                @AuthenticationPrincipal Principal userDetails) {
        applicationService.approveApplication(applicationId, userDetails.getUserId());
        return ApiResponse.noContent();
    }

    @PostMapping("/{applicationId}/reject")
    public ApiResponse<Void> rejectApplication(@PathVariable("applicationId") Long applicationId,
                                               @AuthenticationPrincipal Principal userDetails) {
        applicationService.rejectApplication(applicationId, userDetails.getUserId());
        return ApiResponse.noContent();
    }
}
