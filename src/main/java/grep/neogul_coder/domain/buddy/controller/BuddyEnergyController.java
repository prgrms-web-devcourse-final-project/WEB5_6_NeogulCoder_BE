package grep.neogul_coder.domain.buddy.controller;

import grep.neogul_coder.domain.buddy.controller.dto.response.BuddyEnergyResponse;
import grep.neogul_coder.global.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buddy-energy")
public class BuddyEnergyController implements BuddyEnergySpecification {

    @GetMapping("/{userId}")
    public ApiResponse<BuddyEnergyResponse> get(@PathVariable("userId") Long userId) {
        return ApiResponse.success(new BuddyEnergyResponse());
    }
}