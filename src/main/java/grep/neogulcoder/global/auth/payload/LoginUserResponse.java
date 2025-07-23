package grep.neogulcoder.global.auth.payload;

import grep.neogulcoder.global.auth.code.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginUserResponse {
    private Long userId;
    private String email;
    private String nickname;
    private String profileImage;
    private Role role;
}
