package grep.neogul_coder.domain.users.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePasswordRequest {

    @NotBlank(message = "현재 비밀번호를 입력해주세요")
    String password;

    @NotBlank(message = "변겯할 비밀번호를 입력해주세요")
    String newPassword;

    @NotBlank(message = "비밀번호 확인을 입력해주세요")
    String newPasswordCheck;

}
