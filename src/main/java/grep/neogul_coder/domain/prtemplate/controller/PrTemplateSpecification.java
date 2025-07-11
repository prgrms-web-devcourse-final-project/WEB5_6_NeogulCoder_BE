package grep.neogul_coder.domain.prtemplate.controller;

import grep.neogul_coder.domain.prtemplate.controller.dto.request.IntroductionUpdateRequest;
import grep.neogul_coder.domain.prtemplate.controller.dto.request.PrUpdateRequest;
import grep.neogul_coder.domain.prtemplate.controller.dto.response.PrPageResponse;
import grep.neogul_coder.domain.prtemplate.entity.PrTemplate;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "PR_Template", description = "PR 템플릿 API")
public interface PrTemplateSpecification {

    @Operation(summary = "PR 템플릿 조회", description = "PR 템플릿을 조회합니다.")
    ApiResponse<PrPageResponse> get(@PathVariable("id") Long id);

    @Operation(summary = "PR 정보 수정", description = "PR 정보를 수정합니다.")
    ApiResponse<Void> update(@PathVariable("id") Long id, @RequestBody PrUpdateRequest request);

    @Operation(summary = "PR 소개글 수정", description = "PR 소개글을 수정합니다.")
    ApiResponse<Void> updateIntroduction(@PathVariable("id") Long id, @RequestBody IntroductionUpdateRequest request);

}