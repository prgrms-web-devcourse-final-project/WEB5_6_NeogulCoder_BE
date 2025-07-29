package grep.neogulcoder.domain.buddy.controller;

import grep.neogulcoder.domain.buddy.controller.dto.response.BuddyEnergyResponse;
import grep.neogulcoder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface BuddyEnergySpecification {

    @Operation(summary = "버디 에너지 조회", description = "특정 사용자의 버디 에너지를 조회합니다")
    ResponseEntity<ApiResponse<BuddyEnergyResponse>> get(@PathVariable Long userId);

}
