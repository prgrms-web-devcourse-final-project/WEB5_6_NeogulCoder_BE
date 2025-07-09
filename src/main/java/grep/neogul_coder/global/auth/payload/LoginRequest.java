package grep.neogul_coder.global.auth.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
}
