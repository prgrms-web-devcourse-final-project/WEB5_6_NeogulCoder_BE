package grep.neogul_coder.domain.studyapplication.controller;

import grep.neogul_coder.domain.studyapplication.controller.dto.request.StudyApplicationCreateRequest;
import grep.neogul_coder.domain.studyapplication.controller.dto.response.MyStudyApplicationResponse;
import grep.neogul_coder.domain.studyapplication.controller.dto.response.StudyApplicationResponse;
import grep.neogul_coder.global.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/applications")
@RestController
public class StudyApplicationController implements StudyApplicationSpecification {

    @GetMapping
    public ApiResponse<List<StudyApplicationResponse>> getApplicationList() {
        return ApiResponse.success(List.of(new StudyApplicationResponse()));
    }

    @GetMapping("/me")
    public ApiResponse<List<MyStudyApplicationResponse>> getMyStudyApplicationList() {
        return ApiResponse.success(List.of(new MyStudyApplicationResponse()));
    }

    @PostMapping
    public ApiResponse<Void> createApplication(@RequestBody @Valid StudyApplicationCreateRequest request) {
        return ApiResponse.noContent();
    }
}
