package grep.neogulcoder.domain.users.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordRequest {

    @NotBlank(message = "비밀번호를 입력하세요")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력하세요")
    private String passwordCheck;

}
