package grep.neogul_coder.domain.studyapplication.controller;

import grep.neogul_coder.domain.studyapplication.controller.dto.request.StudyApplicationCreateRequest;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "StudyApplication", description = "스터디 신청 API")
public interface StudyApplicationSpecification {

    @Operation(summary = "스터디 신청 생성", description = "스터디를 신청합니다.")
    ApiResponse<Void> createApplication(StudyApplicationCreateRequest request);
}
