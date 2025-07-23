package grep.neogulcoder.domain.studypost.comment.controller;

import grep.neogulcoder.domain.studypost.comment.dto.StudyPostCommentSaveRequest;
import grep.neogulcoder.domain.studypost.comment.dto.request.StudyCommentUpdateRequest;
import grep.neogulcoder.domain.studypost.comment.service.StudyPostCommentService;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/studies/posts")
public class StudyPostCommentController implements StudyPostCommentSpecification {

    private final StudyPostCommentService commentService;

    @PostMapping("/{post-id}/comments")
    public ApiResponse<Long> create(@PathVariable("post-id") long postId,
                                    @RequestBody @Valid StudyPostCommentSaveRequest request,
                                    @AuthenticationPrincipal Principal userDetails) {
        Long commentId = commentService.create(request, postId, userDetails.getUserId());
        return ApiResponse.success(commentId);
    }

    @PutMapping("/comments/{comment-id}")
    public ApiResponse<Void> update(@PathVariable("comment-id") long commentId,
                                    @RequestBody @Valid StudyCommentUpdateRequest request,
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
