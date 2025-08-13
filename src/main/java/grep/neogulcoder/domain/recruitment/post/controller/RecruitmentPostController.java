package grep.neogulcoder.domain.recruitment.post.controller;

import grep.neogulcoder.domain.recruitment.post.controller.dto.request.RecruitmentPostStatusUpdateRequest;
import grep.neogulcoder.domain.recruitment.post.controller.dto.request.RecruitmentPostUpdateRequest;
import grep.neogulcoder.domain.recruitment.post.controller.dto.response.RecruitmentPostInfo;
import grep.neogulcoder.domain.recruitment.post.controller.dto.response.RecruitmentPostPagingInfo;
import grep.neogulcoder.domain.recruitment.post.service.RecruitmentPostService;
import grep.neogulcoder.domain.study.enums.Category;
import grep.neogulcoder.domain.study.enums.StudyType;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/recruitment-posts")
@RequiredArgsConstructor
@RestController
public class RecruitmentPostController implements RecruitmentPostSpecification {

    private final RecruitmentPostService recruitmentPostService;

    @GetMapping
    public ResponseEntity<ApiResponse<RecruitmentPostPagingInfo>> search(@PageableDefault(size = 10) Pageable pageable,
                                                                         @RequestParam(value = "category", required = false) Category category,
                                                                         @RequestParam(value = "studyType", required = false) StudyType studyType,
                                                                         @RequestParam(value = "keyword", required = false) String keyword) {
        RecruitmentPostPagingInfo response = recruitmentPostService.search(
                pageable, category, studyType, keyword, null
        );
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<RecruitmentPostPagingInfo>> searchMyRecruitmentPost(@PageableDefault(size = 10) Pageable pageable,
                                                                                          @RequestParam(value = "category", required = false) Category category,
                                                                                          @RequestParam(value = "studyType", required = false) StudyType studyType,
                                                                                          @RequestParam(value = "keyword", required = false) String keyword,
                                                                                          @AuthenticationPrincipal Principal userDetails) {
        RecruitmentPostPagingInfo response = recruitmentPostService.search(
                pageable, category, studyType,
                keyword, userDetails.getUserId()
        );
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{recruitment-post-id}")
    public ResponseEntity<ApiResponse<RecruitmentPostInfo>> get(@PathVariable("recruitment-post-id") long recruitmentPostId) {
        RecruitmentPostInfo response = recruitmentPostService.get(recruitmentPostId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{recruitment-post-id}")
    public ResponseEntity<ApiResponse<Long>> update(@PathVariable("recruitment-post-id") long recruitmentPostId,
                                                    @Valid @RequestBody RecruitmentPostUpdateRequest request,
                                                    @AuthenticationPrincipal Principal userDetails) {
        long postId = recruitmentPostService.update(request.toServiceRequest(), recruitmentPostId, userDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.success(postId));
    }

    @DeleteMapping("/{recruitment-post-id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("recruitment-post-id") long recruitmentPostId,
                                                    @AuthenticationPrincipal Principal userDetails) {
        recruitmentPostService.delete(recruitmentPostId, userDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    @PutMapping("/{recruitment-post-id}/status")
    public ResponseEntity<ApiResponse<Long>> changeStatus(@PathVariable("recruitment-post-id") long recruitmentPostId,
                                                          @RequestBody RecruitmentPostStatusUpdateRequest request,
                                                          @AuthenticationPrincipal Principal userDetails) {
        long postId = recruitmentPostService.updateStatus(request.toServiceRequest(), recruitmentPostId, userDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.success(postId));
    }
}
