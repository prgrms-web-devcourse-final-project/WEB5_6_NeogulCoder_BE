package grep.neogul_coder.domain.studyapplication.controller;

import grep.neogul_coder.domain.studyapplication.controller.dto.request.ApplicationCreateRequest;
import grep.neogul_coder.domain.studyapplication.controller.dto.response.MyApplicationResponse;
import grep.neogul_coder.global.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/applications")
@RestController
public class ApplicationController implements ApplicationSpecification {

    @GetMapping("/me")
    public ApiResponse<List<MyApplicationResponse>> getMyStudyApplications() {
        return ApiResponse.success(List.of(new MyApplicationResponse()));
    }

    @PostMapping
    public ApiResponse<Void> createApplication(@RequestBody @Valid ApplicationCreateRequest request) {
        return ApiResponse.noContent();
    }

    @PostMapping("/{applicationId}/approve")
    public ApiResponse<Void> approveApplication(@PathVariable("applicationId") Long applicationId) {
        return ApiResponse.noContent();
    }

    @PostMapping("/{applicationId}/reject")
    public ApiResponse<Void> rejectApplication(@PathVariable("applicationId") Long applicationId) {
        return ApiResponse.noContent();
    }
}
