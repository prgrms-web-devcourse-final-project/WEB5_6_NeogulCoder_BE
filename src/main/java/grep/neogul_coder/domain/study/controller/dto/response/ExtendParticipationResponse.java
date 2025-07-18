package grep.neogul_coder.domain.study.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ExtendParticipationResponse {

    @Schema(description = "유저 Id", example = "1")
    private Long userId;

    @Schema(description = "닉네임", example = "너굴")
    private String nickname;

    @Schema(description = "참여 여부", example = "true")
    private boolean isParticipated;
}
