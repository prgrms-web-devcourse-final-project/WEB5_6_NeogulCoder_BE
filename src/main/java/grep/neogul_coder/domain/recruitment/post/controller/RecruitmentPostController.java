package grep.neogul_coder.domain.recruitment.post.controller;

import grep.neogul_coder.domain.recruitment.post.controller.dto.request.RecruitmentPostCreateRequest;
import grep.neogul_coder.domain.recruitment.post.controller.dto.request.RecruitmentPostStatusUpdateRequest;
import grep.neogul_coder.domain.recruitment.post.controller.dto.request.RecruitmentPostUpdateRequest;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.RecruitmentPostInfo;
import grep.neogul_coder.domain.recruitment.post.service.RecruitmentPostService;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/recruitment-posts")
@RequiredArgsConstructor
@RestController
public class RecruitmentPostController implements RecruitmentPostSpecification {

    private final RecruitmentPostService recruitmentPostService;

    @GetMapping("/{recruitment-post-id}")
    public ApiResponse<RecruitmentPostInfo> get(@PathVariable("recruitment-post-id") long recruitmentPostId) {
        return ApiResponse.success(new RecruitmentPostInfo());
    }

    @PostMapping
    public ApiResponse<Void> save(@Valid @RequestBody RecruitmentPostCreateRequest request,
                                  @AuthenticationPrincipal Principal userDetails) {
        recruitmentPostService.create(request.toServiceRequest(), userDetails.getUserId());
        return ApiResponse.noContent();
    }

    @PutMapping("/{recruitment-post-id}")
    public ApiResponse<Void> update(@PathVariable("recruitment-post-id") long recruitmentPostId,
                                    @Valid @RequestBody RecruitmentPostUpdateRequest request,
                                    @AuthenticationPrincipal Principal userDetails) {
        recruitmentPostService.update(request.toServiceRequest(), recruitmentPostId, userDetails.getUserId());
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{recruitment-post-id}")
    public ApiResponse<Void> delete(@PathVariable("recruitment-post-id") long recruitmentPostId,
                                    @AuthenticationPrincipal Principal userDetails) {
        recruitmentPostService.delete(recruitmentPostId, userDetails.getUserId());
        return ApiResponse.noContent();
    }

    @PostMapping("/{recruitment-post-id}/status")
    public ApiResponse<Void> changeStatus(@PathVariable("recruitment-post-id") long recruitmentPostId,
                                          @RequestBody RecruitmentPostStatusUpdateRequest request,
                                          @AuthenticationPrincipal Principal userDetails) {
        recruitmentPostService.updateStatus(request.toServiceRequest(), recruitmentPostId, userDetails.getUserId());
        return ApiResponse.noContent();
    }
}
