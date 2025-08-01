package grep.neogulcoder.domain.studypost.controller;

import grep.neogulcoder.domain.studypost.Category;
import grep.neogulcoder.domain.studypost.controller.dto.request.StudyPostSaveRequest;
import grep.neogulcoder.domain.studypost.controller.dto.request.StudyPostUpdateRequest;
import grep.neogulcoder.domain.studypost.controller.dto.response.MyStudyPostPagingResult;
import grep.neogulcoder.domain.studypost.controller.dto.response.PostPagingResult;
import grep.neogulcoder.domain.studypost.controller.dto.response.StudyPostDetailResponse;
import grep.neogulcoder.domain.studypost.service.StudyPostService;
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

@RequiredArgsConstructor
@RequestMapping("/api/studies")
@RestController
public class StudyPostController implements StudyPostSpecification {

    private final StudyPostService studyPostService;

    @GetMapping("/{study-id}/posts/search/me")
    public ResponseEntity<ApiResponse<MyStudyPostPagingResult>> getMyPostSearch(@PathVariable("study-id") long studyId,
                                                                @PageableDefault(size = 10) Pageable pageable,
                                                                @RequestParam(value = "category", required = false) Category category,
                                                                @RequestParam(value = "keyword", required = false) String keyword,
                                                                @AuthenticationPrincipal Principal userDetails) {

        MyStudyPostPagingResult response = studyPostService.findMyPagingInfo(studyId, pageable, category, keyword, userDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{study-id}/posts")
    public ResponseEntity<ApiResponse<Long>> create(@PathVariable("study-id") long studyId,
                                    @RequestBody @Valid StudyPostSaveRequest request,
                                    @AuthenticationPrincipal Principal userDetails) {
        long postId = studyPostService.create(request, studyId, userDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.success(postId));
    }

    @GetMapping("/{study-id}/posts/search")
    public ResponseEntity<ApiResponse<PostPagingResult>> findPagingInfo(@PathVariable("study-id") Long studyId,
                                                        @PageableDefault(size = 10) Pageable pageable,
                                                        @RequestParam(value = "category", required = false) Category category,
                                                        @RequestParam(value = "keyword", required = false) String keyword) {

        PostPagingResult response = studyPostService.findPagingInfo(studyId, pageable, category, keyword);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/posts/{post-id}")
    public ResponseEntity<ApiResponse<StudyPostDetailResponse>> findOne(@PathVariable("post-id") Long postId) {
        StudyPostDetailResponse response = studyPostService.findOne(postId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/posts/{post-id}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable("post-id") Long postId,
                                    @RequestBody @Valid StudyPostUpdateRequest request,
                                    @AuthenticationPrincipal Principal userDetails) {
        studyPostService.update(request, postId, userDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    @DeleteMapping("/posts/{post-id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("post-id") Long postId,
                                    @AuthenticationPrincipal Principal userDetails) {
        studyPostService.delete(postId, userDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    @PostMapping(value = "/posts/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> uploadPostImage(@RequestPart("file") MultipartFile file,
                                               @AuthenticationPrincipal Principal userDetail) throws IOException {
        String imageUrl = studyPostService.uploadPostImage(file, userDetail.getUserId());
        return ResponseEntity.ok(ApiResponse.success(imageUrl));
    }
}
