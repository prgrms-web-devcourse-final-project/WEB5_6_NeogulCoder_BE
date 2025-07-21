package grep.neogul_coder.domain.buddy.controller;

import grep.neogul_coder.domain.buddy.controller.dto.response.BuddyEnergyResponse;
import grep.neogul_coder.domain.buddy.enums.BuddyEnergyReason;
import grep.neogul_coder.domain.buddy.service.BuddyEnergyService;
import grep.neogul_coder.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/buddy-energy")
public class BuddyEnergyController implements BuddyEnergySpecification {

    private final BuddyEnergyService buddyEnergyService;

    @GetMapping("/{userId}")
    public ApiResponse<BuddyEnergyResponse> get(@PathVariable("userId") Long userId) {
        return ApiResponse.success(buddyEnergyService.getBuddyEnergy(userId));
    }



}