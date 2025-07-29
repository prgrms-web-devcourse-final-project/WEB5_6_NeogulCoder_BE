package grep.neogulcoder.domain.prtemplate.controller;

import grep.neogulcoder.domain.prtemplate.controller.dto.request.IntroductionUpdateRequest;
import grep.neogulcoder.domain.prtemplate.controller.dto.request.PrUpdateRequest;
import grep.neogulcoder.domain.prtemplate.controller.dto.response.PrPageResponse;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "PR_Template", description = "PR 템플릿 API")
public interface PrTemplateSpecification {

    @Operation(summary = "PR 템플릿 조회", description = "PR 템플릿을 조회합니다.")
    ResponseEntity<ApiResponse<PrPageResponse>> get(@AuthenticationPrincipal Principal principal);

    @Operation(summary = "PR 정보 수정", description = "PR 정보를 수정합니다.")
    ApiResponse<Void> update(@AuthenticationPrincipal Principal principal, @RequestBody PrUpdateRequest request);

    @Operation(summary = "PR 소개글 수정", description = "PR 소개글을 수정합니다.")
    ApiResponse<Void> updateIntroduction(@AuthenticationPrincipal Principal principal, @RequestBody IntroductionUpdateRequest request);

}