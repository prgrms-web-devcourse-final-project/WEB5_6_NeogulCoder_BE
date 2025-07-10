package grep.neogul_coder.domain.study.controller;

import grep.neogul_coder.domain.study.controller.dto.request.StudyCreateRequest;
import grep.neogul_coder.domain.study.controller.dto.request.StudyEditRequest;
import grep.neogul_coder.domain.study.controller.dto.response.StudyResponse;
import grep.neogul_coder.global.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/studies")
@RestController
public class StudyController implements StudySpecification {

    @GetMapping("/{studyId}")
    public ApiResponse<StudyResponse> getStudy(@PathVariable("studyId") Long studyId) {
        return ApiResponse.success(new StudyResponse());
    }

    @PostMapping("/create")
    public ApiResponse<Void> createStudy(@RequestBody StudyCreateRequest request) {
        return ApiResponse.noContent("스터디가 생성되었습니다.");
    }

    @PutMapping("/{studyId}")
    public ApiResponse<Void> editStudy(@PathVariable("studyId") Long studyId,
                                       @RequestBody StudyEditRequest request) {
        return ApiResponse.noContent("스터디가 수정되었습니다.");
    }

    @DeleteMapping("/{studyId}")
    public ApiResponse<Void> deleteStudy(@PathVariable("studyId") Long studyId) {
        return ApiResponse.noContent("스터디가 삭제되었습니다.");
    }
}
