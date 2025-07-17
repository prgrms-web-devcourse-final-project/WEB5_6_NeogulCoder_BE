package grep.neogul_coder.domain.recruitment.comment.controller;

import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Recruitment-Post-Comment", description = "모집글 댓글 API")
public interface RecruitmentPostCommentSpecification {

    @Operation(summary = "모집글 댓글 작성", description = "모집글에 대한 댓글을 작성 합니다.")
    ApiResponse<Void> save(Principal userDetails);
}
