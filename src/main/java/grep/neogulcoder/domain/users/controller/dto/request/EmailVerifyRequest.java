package grep.neogulcoder.domain.users.controller.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EmailVerifyRequest {

    @NotBlank(message = "이메일은 필수입니다")
    @Email(message = "올바른 이메일 형식이어야 합니다")
    private String email;

    @NotBlank(message = "인증코드는 필수입니다")
    private String code;

    public EmailVerifyRequest() {
    }

    @Builder
    private EmailVerifyRequest(String email, String code) {
        this.email = email;
        this.code = code;
    }

    public static EmailVerifyRequest of(String email, String code){
        return EmailVerifyRequest.builder()
                .email(email)
                .code(code)
                .build();
    }
}
