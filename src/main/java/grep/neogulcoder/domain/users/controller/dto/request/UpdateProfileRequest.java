package grep.neogulcoder.domain.users.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "유저 프로필 변경")
public class UpdateProfileRequest {

    @Schema(description = "닉네임", example = "example")
    private String nickname;

    @Schema(description = "프로필 이미지")
    private String profileImgUrl;

}