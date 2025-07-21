package grep.neogul_coder.domain.studypost.comment.controller;

import grep.neogul_coder.domain.studypost.comment.dto.CommentRequest;
import grep.neogul_coder.domain.studypost.comment.dto.CommentResponse;
import grep.neogul_coder.global.response.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/studies/{studyId}/posts/{postId}/comments")
public class CommentController implements CommentSpecification {

  @PostMapping
  public ApiResponse<Void> createComment(
      @PathVariable("studyId") Long studyId,
      @PathVariable("postId") Long postId,
      @RequestBody @Valid CommentRequest request
  ) {
    return ApiResponse.create();
  }

  @GetMapping("/{commentId}")
  public ApiResponse<CommentResponse> findComment(
      @PathVariable("studyId") Long studyId,
      @PathVariable("postId") Long postId,
      @PathVariable("commentId") Long commentId
  ) {
    return ApiResponse.success(new CommentResponse());
  }

  @GetMapping("/all")
  public ApiResponse<List<CommentResponse>> findAllComments(
      @PathVariable("studyId") Long studyId,
      @PathVariable("postId") Long postId
  ) {
    return ApiResponse.success(List.of(new CommentResponse()));
  }

  @DeleteMapping("/{commentId}")
  public ApiResponse<Void> deleteComment(
      @PathVariable("studyId") Long studyId,
      @PathVariable("postId") Long postId,
      @PathVariable("commentId") Long commentId
  ) {
    return ApiResponse.noContent();
  }
}
