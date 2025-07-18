package grep.neogul_coder.domain.study.controller;

import grep.neogul_coder.domain.study.controller.dto.request.StudyCreateRequest;
import grep.neogul_coder.domain.study.controller.dto.request.StudyUpdateRequest;
import grep.neogul_coder.domain.study.controller.dto.response.*;
import grep.neogul_coder.domain.study.service.StudyService;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/studies")
@RequiredArgsConstructor
@RestController
public class StudyController implements StudySpecification {

    private final StudyService studyService;

    @GetMapping
    public ApiResponse<StudyItemPagingResponse> getStudies(@PageableDefault(size = 12) Pageable pageable,
                                                           @AuthenticationPrincipal Principal userDetails) {
        Long userId = userDetails.getUserId();
        StudyItemPagingResponse studies = studyService.getMyStudiesPaging(pageable, userId);
        return ApiResponse.success(studies);
    }

    @GetMapping("/{studyId}/header")
    public ApiResponse<StudyHeaderResponse> getStudyHeader(@PathVariable("studyId") Long studyId) {
        return ApiResponse.success(studyService.getStudyHeader(studyId));
    }

    @GetMapping("/{studyId}")
    public ApiResponse<StudyResponse> getStudy(@PathVariable("studyId") Long studyId) {
        return ApiResponse.success(new StudyResponse());
    }

    @GetMapping("/me/images")
    public ApiResponse<List<StudyImageResponse>> getStudyImages(@AuthenticationPrincipal Principal userDetails) {
        Long userId = userDetails.getUserId();
        return ApiResponse.success(studyService.getStudyImages(userId));
    }

    @GetMapping("/{studyId}/info")
    public ApiResponse<StudyInfoResponse> getStudyInfo(@PathVariable("studyId") Long studyId,
                                                       @AuthenticationPrincipal Principal userDetails) {
        Long userId = userDetails.getUserId();
        return ApiResponse.success(studyService.getMyStudyContent(studyId, userId));
    }

    @GetMapping("/{studyId}/content")
    public ApiResponse<StudyMyContentResponse> getMyStudyContent(@PathVariable("studyId") Long studyId) {
        return ApiResponse.success(new StudyMyContentResponse());
    }

    @GetMapping("/{studyId}/me")
    public ApiResponse<StudyMemberInfoResponse> getMyStudyMemberInfo(@PathVariable("studyId") Long studyId,
                                                                     @AuthenticationPrincipal Principal userDetails) {
        Long userId = userDetails.getUserId();
        return ApiResponse.success(studyService.getMyStudyMemberInfo(studyId, userId));
    }

    @PostMapping
    public ApiResponse<Long> createStudy(@RequestBody @Valid StudyCreateRequest request,
                                         @AuthenticationPrincipal Principal userDetails) {
        Long id = studyService.createStudy(request, userDetails.getUserId());
        return ApiResponse.success(id);
    }

    @PutMapping("/{studyId}")
    public ApiResponse<Void> updateStudy(@PathVariable("studyId") Long studyId,
                                         @RequestBody @Valid StudyUpdateRequest request,
                                         @AuthenticationPrincipal Principal userDetails) {
        studyService.updateStudy(studyId, request, userDetails.getUserId());
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{studyId}")
    public ApiResponse<Void> deleteStudy(@PathVariable("studyId") Long studyId,
                                         @AuthenticationPrincipal Principal userDetails) {
        studyService.deleteStudy(studyId, userDetails.getUserId());
        return ApiResponse.noContent();
    }
}
