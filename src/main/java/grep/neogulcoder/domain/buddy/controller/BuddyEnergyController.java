package grep.neogulcoder.domain.buddy.controller;

import grep.neogulcoder.domain.buddy.controller.dto.response.BuddyEnergyResponse;
import grep.neogulcoder.domain.buddy.service.BuddyEnergyService;
import grep.neogulcoder.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/buddy-energy")
public class BuddyEnergyController implements BuddyEnergySpecification {

    private final BuddyEnergyService buddyEnergyService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<BuddyEnergyResponse>> get(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(ApiResponse.success(buddyEnergyService.getBuddyEnergy(userId)));
    }



}