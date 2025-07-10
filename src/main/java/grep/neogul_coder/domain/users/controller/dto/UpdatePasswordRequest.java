package grep.neogul_coder.domain.users.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "비밀번호 변경")
public class UpdatePasswordRequest {

    @NotBlank(message = "현재 비밀번호를 입력해주세요")
    @Schema(description = "현재 비밀번호", example = "oldPassword")
    String password;

    @NotBlank(message = "변겯할 비밀번호를 입력해주세요")
    @Schema(description = "새 비밀번호", example = "newPassword")
    String newPassword;

    @NotBlank(message = "비밀번호 확인을 입력해주세요")
    @Schema(description = "새 비밀번호 확인", example = "newPassword")
    String newPasswordCheck;

}
