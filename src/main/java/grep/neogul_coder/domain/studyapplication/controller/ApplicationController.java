package grep.neogul_coder.domain.studyapplication.controller;

import grep.neogul_coder.domain.studyapplication.controller.dto.request.ApplicationCreateRequest;
import grep.neogul_coder.domain.studyapplication.controller.dto.response.MyApplicationResponse;
import grep.neogul_coder.domain.studyapplication.controller.dto.response.ApplicationResponse;
import grep.neogul_coder.global.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class ApplicationController implements ApplicationSpecification {

    @GetMapping("/recruitment-posts/{recruitment-post-id}/applications")
    public ApiResponse<List<ApplicationResponse>> getApplications(@PathVariable("recruitment-post-id") Long recruitmentPostId) {
        return ApiResponse.success(List.of(new ApplicationResponse()));
    }

    @GetMapping("/applications/me")
    public ApiResponse<List<MyApplicationResponse>> getMyStudyApplications() {
        return ApiResponse.success(List.of(new MyApplicationResponse()));
    }

    @PostMapping("/applications")
    public ApiResponse<Void> createApplication(@RequestBody @Valid ApplicationCreateRequest request) {
        return ApiResponse.noContent();
    }
}
