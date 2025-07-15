package grep.neogul_coder.domain.users.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description = "회원가입")
public class SignUpRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email
    @Schema(description = "이메일", example = "example@example.com")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Schema(description = "비밀번호", example = "example123")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    @Schema(description = "비밀번호 확인", example = "example123")
    private String passwordCheck;

    @NotBlank(message = "닉네임은 필수 입력 사항입니다.")
    @Size(min = 2, max = 10, message = "닉네임은 최소 2자, 최대 10자까지 입력 가능합니다.")
    @Schema(description = "닉네임", example = "홍길동")
    private String nickname;

}