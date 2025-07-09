package grep.neogul_coder.global.auth.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TokenDto {
    private String accessToken;
    private String refreshToken;
    private String atId;
    private String grantType;
    private Long expiresIn;
    private Long refreshExpiresIn;
}
