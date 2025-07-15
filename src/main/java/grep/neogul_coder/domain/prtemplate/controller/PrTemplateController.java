package grep.neogul_coder.domain.prtemplate.controller;

import grep.neogul_coder.domain.prtemplate.controller.dto.request.IntroductionUpdateRequest;
import grep.neogul_coder.domain.prtemplate.controller.dto.request.PrUpdateRequest;
import grep.neogul_coder.domain.prtemplate.controller.dto.response.PrPageResponse;
import grep.neogul_coder.domain.prtemplate.entity.PrTemplate;
import grep.neogul_coder.domain.prtemplate.service.LinkService;
import grep.neogul_coder.domain.prtemplate.service.PrTemplateService;
import grep.neogul_coder.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/prtemplate")
public class PrTemplateController implements PrTemplateSpecification {

    private final PrTemplateService prTemplateService;
    private final LinkService linkService;

    @GetMapping("{id}")
    public ApiResponse<PrPageResponse> get(@PathVariable("id") Long id) {
        PrPageResponse PrPageResponse = null;
        return ApiResponse.success(PrPageResponse);
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> update(@PathVariable("id") Long id,
        @RequestBody PrUpdateRequest request) {
        prTemplateService.update(id,request.getLocation());
        linkService.update(id,request.getPrUrls());
        return ApiResponse.noContent();
    }

    @PutMapping("/introduction/{id}")
    public ApiResponse<Void> updateIntroduction(@PathVariable("id") Long id,
        @RequestBody IntroductionUpdateRequest request){
        prTemplateService.updateIntroduction(id,request.getIntroduction());
        return ApiResponse.noContent();
    }
}