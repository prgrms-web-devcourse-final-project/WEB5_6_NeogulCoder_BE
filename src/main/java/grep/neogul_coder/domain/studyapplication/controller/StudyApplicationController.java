package grep.neogul_coder.domain.studyapplication.controller;

import grep.neogul_coder.domain.studyapplication.controller.dto.request.StudyApplicationCreateRequest;
import grep.neogul_coder.global.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/application")
@RestController
public class StudyApplicationController implements StudyApplicationSpecification {

    @PostMapping
    public ApiResponse<Void> createApplication(@RequestBody @Valid StudyApplicationCreateRequest request) {
        return ApiResponse.noContent();
    }
}
