package grep.neogul_coder.domain.study.controller;

import grep.neogul_coder.domain.study.controller.dto.request.StudyCreateRequest;
import grep.neogul_coder.domain.study.controller.dto.request.StudyEditRequest;
import grep.neogul_coder.domain.study.controller.dto.response.StudyResponse;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Study", description = "스터디 API")
public interface StudySpecification {

    @Operation(summary = "스터디 조회", description = "스터디를 조회합니다.")
    ApiResponse<StudyResponse> getStudy(@PathVariable("studyId") Long studyId);

    @Operation(summary = "스터디 생성", description = "새로운 스터디를 생성합니다.")
    ApiResponse<Void> createStudy(StudyCreateRequest request);

    @Operation(summary = "스터디 수정", description = "스터디를 수정합니다.")
    ApiResponse<Void> editStudy(@PathVariable("studyId") Long studyId, StudyEditRequest request);
}
