package grep.neogulcoder.domain.recruitment.comment.controller;

import grep.neogulcoder.domain.recruitment.comment.controller.dto.request.RecruitmentCommentSaveRequest;
import grep.neogulcoder.domain.recruitment.comment.controller.dto.request.RecruitmentCommentUpdateRequest;
import grep.neogulcoder.domain.recruitment.comment.service.RecruitmentPostCommentService;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/recruitment-posts")
@RequiredArgsConstructor
@RestController
public class RecruitmentPostCommentController implements RecruitmentPostCommentSpecification {

    private final RecruitmentPostCommentService commentService;

    @PostMapping("/{post-id}/comments")
    public ApiResponse<Long> save(@PathVariable("post-id") long postId,
                                  @RequestBody @Valid RecruitmentCommentSaveRequest request,
                                  @AuthenticationPrincipal Principal userDetails) {
        long commentId = commentService.save(postId, request, userDetails.getUserId());
        return ApiResponse.success(commentId);
    }

    @PutMapping("/comments/{comment-id}")
    public ApiResponse<Void> update(@PathVariable("comment-id") long commentId,
                                    @RequestBody RecruitmentCommentUpdateRequest request,
                                    @AuthenticationPrincipal Principal userDetails) {
        commentService.update(request, commentId, userDetails.getUserId());
        return ApiResponse.noContent();
    }

    @DeleteMapping("/comments/{comment-id}")
    public ApiResponse<Void> delete(@PathVariable("comment-id") long commentId,
                                    @AuthenticationPrincipal Principal userDetails) {
        commentService.delete(commentId, userDetails.getUserId());
        return ApiResponse.noContent();
    }
}
