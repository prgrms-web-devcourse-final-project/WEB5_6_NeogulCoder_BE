package grep.neogulcoder.global.auth.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private TokenResponse token;
    private LoginUserResponse user;
}
