package grep.neogulcoder.domain.prtemplate.controller;

import grep.neogulcoder.domain.prtemplate.controller.dto.request.IntroductionUpdateRequest;
import grep.neogulcoder.domain.prtemplate.controller.dto.request.PrUpdateRequest;
import grep.neogulcoder.domain.prtemplate.controller.dto.response.PrPageResponse;
import grep.neogulcoder.domain.prtemplate.service.LinkService;
import grep.neogulcoder.domain.prtemplate.service.PrTemplateService;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/template")
public class PrTemplateController implements PrTemplateSpecification {

    private final PrTemplateService prTemplateService;
    private final LinkService linkService;

    @GetMapping("/mine")
    public ApiResponse<PrPageResponse> get(@AuthenticationPrincipal Principal principal) {
        PrPageResponse prPageResponse = prTemplateService.toResponse(principal.getUserId());
        return ApiResponse.success(prPageResponse);
    }

    @GetMapping("/{userid}")
    public ApiResponse<PrPageResponse> get(@PathVariable("userid") Long userId) {
        PrPageResponse prPageResponse = prTemplateService.toResponse(userId);
        return ApiResponse.success(prPageResponse);
    }

    @PutMapping("/update/template")
    public ApiResponse<Void> update(@AuthenticationPrincipal Principal principal,
        @RequestBody PrUpdateRequest request) {
        prTemplateService.update(principal.getUserId(), request.getLocation());
        linkService.update(principal.getUserId(), request.getPrUrls());
        return ApiResponse.noContent();
    }

    @PutMapping("/update/introduction")
    public ApiResponse<Void> updateIntroduction(@AuthenticationPrincipal Principal principal,
        @RequestBody IntroductionUpdateRequest request) {
        prTemplateService.updateIntroduction(principal.getUserId(), request.getIntroduction());
        return ApiResponse.noContent();
    }
}