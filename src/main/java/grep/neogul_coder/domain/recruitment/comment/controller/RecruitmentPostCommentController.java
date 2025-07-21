package grep.neogul_coder.domain.recruitment.comment.controller;

import grep.neogul_coder.domain.recruitment.comment.controller.dto.request.RecruitmentCommentSaveRequest;
import grep.neogul_coder.domain.recruitment.comment.controller.dto.request.RecruitmentCommentUpdateRequest;
import grep.neogul_coder.domain.recruitment.comment.service.RecruitmentPostCommentService;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/recruitment-posts/comments")
@RequiredArgsConstructor
@RestController
public class RecruitmentPostCommentController implements RecruitmentPostCommentSpecification {

    private final RecruitmentPostCommentService commentService;

    @PostMapping
    public ApiResponse<Long> save(@RequestBody @Valid RecruitmentCommentSaveRequest request,
                                  @AuthenticationPrincipal Principal userDetails) {
        long commentId = commentService.save(request, userDetails.getUserId());
        return ApiResponse.success(commentId);
    }

    @PutMapping("/{comment-id}")
    public ApiResponse<Void> update(@PathVariable("comment-id") long commentId,
                                    @RequestBody RecruitmentCommentUpdateRequest request,
                                    @AuthenticationPrincipal Principal userDetails) {
        commentService.update(request, commentId, userDetails.getUserId());
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{comment-id}")
    public ApiResponse<Void> delete(@PathVariable("comment-id") long commentId,
                                    @AuthenticationPrincipal Principal userDetails) {
        commentService.delete(commentId, userDetails.getUserId());
        return ApiResponse.noContent();
    }
}
