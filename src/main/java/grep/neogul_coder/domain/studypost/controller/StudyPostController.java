package grep.neogul_coder.domain.studypost.controller;

import grep.neogul_coder.domain.studypost.controller.dto.StudyPostListResponse;
import grep.neogul_coder.domain.studypost.controller.dto.request.StudyPostSaveRequest;
import grep.neogul_coder.domain.studypost.controller.dto.request.StudyPostUpdateRequest;
import grep.neogul_coder.domain.studypost.controller.dto.response.StudyPostDetailResponse;
import grep.neogul_coder.domain.studypost.service.StudyPostService;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/posts")
@RestController
public class StudyPostController implements StudyPostSpecification {

    private final StudyPostService studyPostService;

    @PostMapping
    public ApiResponse<Long> create(@RequestBody @Valid StudyPostSaveRequest request,
                                    @AuthenticationPrincipal Principal userDetails) {
        long postId = studyPostService.create(request, userDetails.getUserId());
        return ApiResponse.success(postId);
    }

    @GetMapping("/{postId}")
    public ApiResponse<StudyPostDetailResponse> findOne(@PathVariable("postId") Long postId) {
        StudyPostDetailResponse response = studyPostService.findOne(postId);
        return ApiResponse.success(response);
    }

    @GetMapping("/all")
    public ApiResponse<List<StudyPostListResponse>> findAllWithoutPagination(
            @PathVariable("studyId") Long studyId
    ) {
        List<StudyPostListResponse> content = List.of(new StudyPostListResponse());
        return ApiResponse.success(content);
    }

    @PutMapping("/{postId}")
    public ApiResponse<Void> update(@PathVariable("postId") Long postId,
                                    @RequestBody @Valid StudyPostUpdateRequest request,
                                    @AuthenticationPrincipal Principal userDetails) {
        studyPostService.update(request, postId, userDetails.getUserId());
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{postId}")
    public ApiResponse<Void> delete(@PathVariable("postId") Long postId,
                                    @AuthenticationPrincipal Principal userDetails) {
        studyPostService.delete(postId, userDetails.getUserId());
        return ApiResponse.noContent();
    }
}
