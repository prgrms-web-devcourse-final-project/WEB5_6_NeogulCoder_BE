package grep.neogulcoder.domain.study.controller;

import grep.neogulcoder.domain.study.controller.dto.request.StudyCreateRequest;
import grep.neogulcoder.domain.study.controller.dto.request.StudyUpdateRequest;
import grep.neogulcoder.domain.study.controller.dto.response.*;
import grep.neogulcoder.domain.study.service.StudyService;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/studies")
@RequiredArgsConstructor
@RestController
public class StudyController implements StudySpecification {

    private final StudyService studyService;

    @GetMapping
    public ResponseEntity<ApiResponse<StudyItemPagingResponse>> getMyStudies(@PageableDefault(size = 12) Pageable pageable,
                                                                             @RequestParam(required = false) Boolean finished,
                                                                             @AuthenticationPrincipal Principal userDetails) {
        StudyItemPagingResponse myStudies = studyService.getMyStudiesPaging(pageable, userDetails.getUserId(), finished);
        return ResponseEntity.ok(ApiResponse.success(myStudies));
    }

    @GetMapping("/main")
    public ResponseEntity<ApiResponse<List<StudyItemResponse>>> getMyUnfinishedStudies(@AuthenticationPrincipal Principal userDetails) {
        List<StudyItemResponse> unfinishedStudies = studyService.getMyUnfinishedStudies(userDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.success(unfinishedStudies));
    }

    @GetMapping("/{studyId}/header")
    public ResponseEntity<ApiResponse<StudyHeaderResponse>> getStudyHeader(@PathVariable("studyId") Long studyId) {
        return ResponseEntity.ok(ApiResponse.success(studyService.getStudyHeader(studyId)));
    }

    @GetMapping("/{studyId}")
    public ResponseEntity<ApiResponse<StudyResponse>> getStudy(@PathVariable("studyId") Long studyId) {
        return ResponseEntity.ok(ApiResponse.success(studyService.getStudy(studyId)));
    }

    @GetMapping("/me/images")
    public ResponseEntity<ApiResponse<List<StudyImageResponse>>> getStudyImages(@AuthenticationPrincipal Principal userDetails) {
        Long userId = userDetails.getUserId();
        return ResponseEntity.ok(ApiResponse.success(studyService.getStudyImages(userId)));
    }

    @GetMapping("/{studyId}/info")
    public ResponseEntity<ApiResponse<StudyInfoResponse>> getStudyInfo(@PathVariable("studyId") Long studyId,
                                                       @AuthenticationPrincipal Principal userDetails) {
        Long userId = userDetails.getUserId();
        return ResponseEntity.ok(ApiResponse.success(studyService.getMyStudyContent(studyId, userId)));
    }

    @GetMapping("/{studyId}/me")
    public ResponseEntity<ApiResponse<StudyMemberInfoResponse>> getMyStudyMemberInfo(@PathVariable("studyId") Long studyId,
                                                                     @AuthenticationPrincipal Principal userDetails) {
        Long userId = userDetails.getUserId();
        return ResponseEntity.ok(ApiResponse.success(studyService.getMyStudyMemberInfo(studyId, userId)));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Long>> createStudy(@RequestPart("request") @Valid StudyCreateRequest request,
                                         @RequestPart(value = "image", required = false) MultipartFile image,
                                         @AuthenticationPrincipal Principal userDetails) throws IOException {
        Long id = studyService.createStudy(request, userDetails.getUserId(), image);
        return ResponseEntity.ok(ApiResponse.success(id));
    }

    @PutMapping(value = "/{studyId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Void>> updateStudy(@PathVariable("studyId") Long studyId,
                                         @RequestPart @Valid StudyUpdateRequest request,
                                         @RequestPart(value = "image", required = false) MultipartFile image,
                                         @AuthenticationPrincipal Principal userDetails) throws IOException {
        studyService.updateStudy(studyId, request, userDetails.getUserId(), image);
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    @DeleteMapping("/{studyId}")
    public ResponseEntity<ApiResponse<Void>> deleteStudy(@PathVariable("studyId") Long studyId,
                                         @AuthenticationPrincipal Principal userDetails) {
        studyService.deleteStudy(studyId, userDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.noContent());
    }
}
