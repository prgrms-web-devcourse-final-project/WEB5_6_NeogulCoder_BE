package grep.neogul_coder.domain.recruitment.post.controller;

import grep.neogul_coder.domain.recruitment.post.controller.dto.request.RecruitmentPostSaveRequest;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Recruitment-Post", description = "모집 글 API")
public interface RecruitmentPostSpecification {

    @Operation(summary = "모집 글 저장", description = "스터디 모집 글을 저장 합니다.")
    ApiResponse<Void> save(Long studyId, RecruitmentPostSaveRequest request);
}
