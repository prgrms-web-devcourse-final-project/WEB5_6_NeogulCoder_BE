package grep.neogul_coder.domain.recruitment.post.controller;

import grep.neogul_coder.domain.recruitment.post.controller.dto.request.PagingCondition;
import grep.neogul_coder.domain.recruitment.post.controller.dto.request.RecruitmentPostStatusUpdateRequest;
import grep.neogul_coder.domain.recruitment.post.controller.dto.request.RecruitmentPostUpdateRequest;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.RecruitmentApplicationPagingInfo;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.RecruitmentPostInfo;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.RecruitmentPostPagingInfo;
import grep.neogul_coder.domain.recruitment.post.service.RecruitmentPostService;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/recruitment-posts")
@RequiredArgsConstructor
@RestController
public class RecruitmentPostController implements RecruitmentPostSpecification {

    private final RecruitmentPostService recruitmentPostService;

    @GetMapping
    public ApiResponse<RecruitmentPostPagingInfo> getPagingInfo(@RequestBody PagingCondition condition) {
        RecruitmentPostPagingInfo response = recruitmentPostService.getPagingInfo(condition, null);
        return ApiResponse.success(response);
    }

    @GetMapping("/me")
    public ApiResponse<RecruitmentPostPagingInfo> getMyPostPagingInfo(@RequestBody PagingCondition condition,
                                                                      @AuthenticationPrincipal Principal userDetails) {
        RecruitmentPostPagingInfo response = recruitmentPostService.getPagingInfo(condition, userDetails.getUserId());
        return ApiResponse.success(response);
    }

    @GetMapping("/{recruitment-post-id}")
    public ApiResponse<RecruitmentPostInfo> get(@PathVariable("recruitment-post-id") long recruitmentPostId) {
        RecruitmentPostInfo response = recruitmentPostService.get(recruitmentPostId);
        return ApiResponse.success(response);
    }

    @PutMapping("/{recruitment-post-id}")
    public ApiResponse<Long> update(@PathVariable("recruitment-post-id") long recruitmentPostId,
                                    @Valid @RequestBody RecruitmentPostUpdateRequest request,
                                    @AuthenticationPrincipal Principal userDetails) {
        long postId = recruitmentPostService.update(request.toServiceRequest(), recruitmentPostId, userDetails.getUserId());
        return ApiResponse.success(postId);
    }

    @DeleteMapping("/{recruitment-post-id}")
    public ApiResponse<Void> delete(@PathVariable("recruitment-post-id") long recruitmentPostId,
                                    @AuthenticationPrincipal Principal userDetails) {
        recruitmentPostService.delete(recruitmentPostId, userDetails.getUserId());
        return ApiResponse.noContent();
    }

    @PutMapping("/{recruitment-post-id}/status")
    public ApiResponse<Long> changeStatus(@PathVariable("recruitment-post-id") long recruitmentPostId,
                                          @RequestBody RecruitmentPostStatusUpdateRequest request,
                                          @AuthenticationPrincipal Principal userDetails) {
        long postId = recruitmentPostService.updateStatus(request.toServiceRequest(), recruitmentPostId, userDetails.getUserId());
        return ApiResponse.success(postId);
    }

    @GetMapping("{recruitment-post-id}/applications")
    public ApiResponse<RecruitmentApplicationPagingInfo> getApplications(@PageableDefault(size = 5) Pageable pageable,
                                                                         @PathVariable("recruitment-post-id") long recruitmentPostId) {
        return ApiResponse.success(new RecruitmentApplicationPagingInfo());
    }

}
