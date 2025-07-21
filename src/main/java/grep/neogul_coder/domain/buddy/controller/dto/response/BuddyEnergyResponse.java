package grep.neogul_coder.domain.buddy.controller.dto.response;

import grep.neogul_coder.domain.buddy.entity.BuddyEnergy;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "버디 에너지 응답 dto")
public class BuddyEnergyResponse {

    @Schema(description = "회원 ID", example = "1")
    private Long userId;

    @Schema(description = "현재 버디에너지 수치", example = "61")
    private int level;

    @Builder
    protected BuddyEnergyResponse(Long userId, int level) {
        this.userId = userId;
        this.level = level;
    }

    public static BuddyEnergyResponse from(BuddyEnergy energy) {
        return BuddyEnergyResponse.builder()
            .userId(energy.getUserId())
            .level(energy.getLevel())
            .build();
    }


}
