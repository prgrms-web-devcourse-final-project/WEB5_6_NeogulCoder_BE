package grep.neogulcoder.domain.recruitment.comment.controller;

import grep.neogulcoder.domain.recruitment.comment.controller.dto.request.RecruitmentCommentSaveRequest;
import grep.neogulcoder.domain.recruitment.comment.controller.dto.request.RecruitmentCommentUpdateRequest;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Recruitment-Post-Comment", description = "모집글 댓글 API")
public interface RecruitmentPostCommentSpecification {

    @Operation(summary = "모집글 댓글 작성", description = "모집글에 대한 댓글을 작성 합니다.")
    ResponseEntity<ApiResponse<Long>> save(long postId, RecruitmentCommentSaveRequest request, Principal userDetails);

    @Operation(summary = "모집글 댓글 수정", description = "모집글에 대한 댓글을 수정 합니다.")
    ResponseEntity<ApiResponse<Void>> update(long commentId, RecruitmentCommentUpdateRequest request, Principal userDetails);

    @Operation(summary = "모집글 댓글 삭제", description = "모집글에 대한 댓글을 삭제 합니다.")
    ResponseEntity<ApiResponse<Void>> delete(long commentId, Principal userDetails);
}
