package grep.neogul_coder.domain.recruitment.comment.controller;

import grep.neogul_coder.domain.recruitment.comment.controller.dto.request.RecruitmentCommentSaveRequest;
import grep.neogul_coder.domain.recruitment.comment.controller.dto.request.RecruitmentCommentUpdateRequest;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/recruitment-posts/{post-id}/comments")
@RestController
public class RecruitmentPostCommentController implements RecruitmentPostCommentSpecification {

    @PostMapping
    public ApiResponse<Void> save(@PathVariable("post-id") long postId,
                                  @RequestBody RecruitmentCommentSaveRequest request,
                                  @AuthenticationPrincipal Principal userDetails) {
        return ApiResponse.noContent();
    }

    @PutMapping("/{comment-id}")
    public ApiResponse<Void> update(@PathVariable("comment-id") long commentId,
                                    @RequestBody RecruitmentCommentUpdateRequest request,
                                    @AuthenticationPrincipal Principal userDetails) {
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{comment-id}")
    public ApiResponse<Void> delete(@PathVariable("comment-id") long commentId,
                                    @AuthenticationPrincipal Principal userDetails) {
        return ApiResponse.noContent();
    }
}
