package grep.neogul_coder.global.auth.payload;

import grep.neogul_coder.domain.users.controller.dto.response.UserResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private TokenResponse token;
    private LoginUserResponse user;
}
