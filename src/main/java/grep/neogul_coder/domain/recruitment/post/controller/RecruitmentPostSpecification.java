package grep.neogul_coder.domain.recruitment.post.controller;

import grep.neogul_coder.domain.recruitment.post.controller.dto.request.RecruitmentPostEditRequest;
import grep.neogul_coder.domain.recruitment.post.controller.dto.request.RecruitmentPostSaveRequest;
import grep.neogul_coder.domain.recruitment.post.controller.dto.request.RecruitmentPostStatusEditRequest;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.RecruitmentPostInfo;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Recruitment-Post", description = "모집 글 API")
public interface RecruitmentPostSpecification {

    @Operation(summary = "모집 글 조회", description = "모집 글의 상세 내역을 조회 합니다.")
    ApiResponse<RecruitmentPostInfo> get(long id);

    @Operation(summary = "모집 글 저장", description = "스터디 모집 글을 저장 합니다.")
    ApiResponse<Void> save(RecruitmentPostSaveRequest request);

    @Operation(summary = "모집 글 수정", description = "모집글을 수정 합니다.")
    ApiResponse<Void> edit(long recruitmentPostId, RecruitmentPostEditRequest request);

    @Operation(summary = "모집 글 삭제", description = "모집글을 삭제 합니다.")
    ApiResponse<Void> delete(long recruitmentPostId);

    @Operation(summary = "모집 상태 변경", description = "모집글의 상태를 변경 합니다.")
    ApiResponse<Void> changeStatus(long recruitmentPostId, RecruitmentPostStatusEditRequest request);
}
