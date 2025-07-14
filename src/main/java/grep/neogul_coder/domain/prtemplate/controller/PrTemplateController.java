package grep.neogul_coder.domain.prtemplate.controller;

import grep.neogul_coder.domain.prtemplate.controller.dto.request.IntroductionUpdateRequest;
import grep.neogul_coder.domain.prtemplate.controller.dto.request.PrUpdateRequest;
import grep.neogul_coder.domain.prtemplate.controller.dto.response.PrPageResponse;
import grep.neogul_coder.domain.prtemplate.entity.PrTemplate;
import grep.neogul_coder.global.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prtemplate")
public class PrTemplateController implements PrTemplateSpecification {

    @GetMapping("{id}")
    public ApiResponse<PrPageResponse> get(@PathVariable("id") Long id) {
        PrPageResponse PrPageResponse = null;
        return ApiResponse.success(PrPageResponse);
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> update(@PathVariable("id") Long id,
        @RequestBody PrUpdateRequest request) {

        return ApiResponse.noContent();
    }

    @PutMapping("/introduction/{id}")
    public ApiResponse<Void> updateIntroduction(@PathVariable("id") Long id,
        @RequestBody IntroductionUpdateRequest request){

        return ApiResponse.noContent();
    }
}
