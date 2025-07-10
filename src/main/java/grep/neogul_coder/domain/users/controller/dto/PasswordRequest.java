package grep.neogul_coder.domain.users.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordRequest {

    @NotBlank(message = "비밀번호를 입력하세요")
    String password;

}
