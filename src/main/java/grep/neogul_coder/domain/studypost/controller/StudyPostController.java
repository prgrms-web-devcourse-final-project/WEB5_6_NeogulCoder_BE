package grep.neogul_coder.domain.studypost.controller;

import grep.neogul_coder.domain.studypost.Category;
import grep.neogul_coder.domain.studypost.controller.dto.request.StudyPostSaveRequest;
import grep.neogul_coder.domain.studypost.controller.dto.request.StudyPostUpdateRequest;
import grep.neogul_coder.domain.studypost.controller.dto.response.MyStudyPostPagingResult;
import grep.neogul_coder.domain.studypost.controller.dto.response.PostPagingResult;
import grep.neogul_coder.domain.studypost.controller.dto.response.StudyPostDetailResponse;
import grep.neogul_coder.domain.studypost.service.StudyPostService;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/api/studies")
@RestController
public class StudyPostController implements StudyPostSpecification {

    private final StudyPostService studyPostService;

    @GetMapping("/{study-id}/posts/search/me")
    public ApiResponse<MyStudyPostPagingResult> getMyPostSearch(@PathVariable("study-id") long studyId,
                                                                @PageableDefault(size = 10) Pageable pageable,
                                                                @RequestParam(value = "category", required = false) Category category,
                                                                @RequestParam(value = "keyword", required = false) String keyword,
                                                                @AuthenticationPrincipal Principal userDetails) {

        MyStudyPostPagingResult response = studyPostService.findMyPagingInfo(studyId, pageable, category, keyword, userDetails.getUserId());
        return ApiResponse.success(response);
    }

    @PostMapping("/{study-id}/posts")
    public ApiResponse<Long> create(@PathVariable("study-id") long studyId,
                                    @RequestBody @Valid StudyPostSaveRequest request,
                                    @AuthenticationPrincipal Principal userDetails) {
        long postId = studyPostService.create(request, studyId, userDetails.getUserId());
        return ApiResponse.success(postId);
    }

    @GetMapping("/{study-id}/posts/search")
    public ApiResponse<PostPagingResult> findPagingInfo(@PathVariable("study-id") Long studyId,
                                                        @PageableDefault(size = 10) Pageable pageable,
                                                        @RequestParam(value = "category", required = false) Category category,
                                                        @RequestParam(value = "keyword", required = false) String keyword) {

        PostPagingResult response = studyPostService.findPagingInfo(studyId, pageable, category, keyword);
        return ApiResponse.success(response);
    }

    @GetMapping("/posts/{post-id}")
    public ApiResponse<StudyPostDetailResponse> findOne(@PathVariable("post-id") Long postId) {
        StudyPostDetailResponse response = studyPostService.findOne(postId);
        return ApiResponse.success(response);
    }

    @PutMapping("/posts/{post-id}")
    public ApiResponse<Void> update(@PathVariable("post-id") Long postId,
                                    @RequestBody @Valid StudyPostUpdateRequest request,
                                    @AuthenticationPrincipal Principal userDetails) {
        studyPostService.update(request, postId, userDetails.getUserId());
        return ApiResponse.noContent();
    }

    @DeleteMapping("/posts/{post-id}")
    public ApiResponse<Void> delete(@PathVariable("post-id") Long postId,
                                    @AuthenticationPrincipal Principal userDetails) {
        studyPostService.delete(postId, userDetails.getUserId());
        return ApiResponse.noContent();
    }

    @PostMapping(value = "/posts/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<String> uploadPostImage(@RequestPart("file") MultipartFile file,
                                               @AuthenticationPrincipal Principal userDetail) throws IOException {
        String imageUrl = studyPostService.uploadPostImage(file, userDetail.getUserId());
        return ApiResponse.success(imageUrl);
    }
}
