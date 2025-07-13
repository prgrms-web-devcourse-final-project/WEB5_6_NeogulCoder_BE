package grep.neogul_coder.domain.study.controller;

import grep.neogul_coder.domain.study.controller.dto.request.StudyCreateRequest;
import grep.neogul_coder.domain.study.controller.dto.request.StudyEditRequest;
import grep.neogul_coder.domain.study.controller.dto.response.*;
import grep.neogul_coder.global.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/studies")
@RestController
public class StudyController implements StudySpecification {

    @GetMapping
    public ApiResponse<List<StudyItemResponse>> getStudies() {
        return ApiResponse.success(List.of(new StudyItemResponse()));
    }

    @GetMapping("/{studyId}/header")
    public ApiResponse<StudyHeaderResponse> getStudyHeader(@PathVariable("studyId") Long studyId) {
        return ApiResponse.success(new StudyHeaderResponse());
    }

    @GetMapping("/{studyId}")
    public ApiResponse<StudyResponse> getStudy(@PathVariable("studyId") Long studyId) {
        return ApiResponse.success(new StudyResponse());
    }

    @GetMapping("/me/images")
    public ApiResponse<List<StudyImageResponse>> getStudyImages() {
        return ApiResponse.success(List.of(new StudyImageResponse()));
    }

    @GetMapping("/{studyId}/info")
    public ApiResponse<StudyInfoResponse> getStudyInfo(@PathVariable("studyId") Long studyId) {
        return ApiResponse.success(new StudyInfoResponse());
    }

    @GetMapping("/{studyId}/me")
    public ApiResponse<StudyMyInfoResponse> getStudyMyInfo(@PathVariable("studyId") Long studyId) {
        return ApiResponse.success(new StudyMyInfoResponse());
    }

    @PostMapping
    public ApiResponse<Void> createStudy(@RequestBody @Valid StudyCreateRequest request) {
        return ApiResponse.noContent();
    }

    @PutMapping("/{studyId}")
    public ApiResponse<Void> editStudy(@PathVariable("studyId") Long studyId,
                                       @RequestBody @Valid StudyEditRequest request) {
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{studyId}")
    public ApiResponse<Void> deleteStudy(@PathVariable("studyId") Long studyId) {
        return ApiResponse.noContent();
    }
}
