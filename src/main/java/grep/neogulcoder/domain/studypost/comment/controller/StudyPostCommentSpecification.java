package grep.neogulcoder.domain.studypost.comment.controller;

import grep.neogulcoder.domain.studypost.comment.dto.StudyPostCommentSaveRequest;
import grep.neogulcoder.domain.studypost.comment.dto.request.StudyCommentUpdateRequest;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Study-Comment", description = "스터디 게시판 댓글 API")
public interface StudyPostCommentSpecification {

    @Operation(summary = "댓글 생성", description = "스터디 게시글에 댓글을 생성합니다.")
    ResponseEntity<ApiResponse<Long>> create(@Parameter(description = "게시글 ID", example = "10") long postId, StudyPostCommentSaveRequest request, Principal userDetails);

    @Operation(summary = "댓글 수정", description = "댓글을 수정 합니다.")
    ResponseEntity<ApiResponse<Void>> update(long commentId, StudyCommentUpdateRequest request, Principal userDetails);

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    ResponseEntity<ApiResponse<Void>> delete(@Parameter(description = "댓글 ID", example = "100") long commentId,
                             Principal userDetails);
}
