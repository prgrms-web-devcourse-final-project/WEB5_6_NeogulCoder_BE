package grep.neogul_coder.domain.study.controller;

import grep.neogul_coder.domain.study.controller.dto.request.StudyCreateRequest;
import grep.neogul_coder.global.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/study")
@RestController
public class StudyController implements StudySpecification {

    @PostMapping("/new")
    public ApiResponse<Void> createStudy(@RequestBody StudyCreateRequest request) {
        return ApiResponse.noContent();
    }
}
