package grep.neogul_coder.domain.studypost.comment.controller;

import grep.neogul_coder.domain.studypost.comment.dto.CommentRequest;
import grep.neogul_coder.domain.studypost.comment.dto.CommentResponse;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "Study-Comment", description = "스터디 게시판 댓글 API")
public interface CommentSpecification {

  @Operation(summary = "댓글 생성", description = "스터디 게시글에 댓글을 생성합니다.")
  ApiResponse<Void> createComment(
      @Parameter(description = "스터디 ID", example = "1") Long studyId,
      @Parameter(description = "게시글 ID", example = "10") Long postId,
      CommentRequest request
  );

  @Operation(summary = "댓글 단건 조회", description = "특정 댓글의 상세 내용을 조회합니다.")
  ApiResponse<CommentResponse> findComment(
      @Parameter(description = "스터디 ID", example = "1") Long studyId,
      @Parameter(description = "게시글 ID", example = "10") Long postId,
      @Parameter(description = "댓글 ID", example = "100") Long commentId
  );

  @Operation(summary = "댓글 목록 전체 조회", description = "해당 게시글의 댓글 전체를 조회합니다.")
  ApiResponse<List<CommentResponse>> findAllComments(
      @Parameter(description = "스터디 ID", example = "1") Long studyId,
      @Parameter(description = "게시글 ID", example = "10") Long postId
  );

  @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
  ApiResponse<Void> deleteComment(
      @Parameter(description = "스터디 ID", example = "1") Long studyId,
      @Parameter(description = "게시글 ID", example = "10") Long postId,
      @Parameter(description = "댓글 ID", example = "100") Long commentId
  );
}
