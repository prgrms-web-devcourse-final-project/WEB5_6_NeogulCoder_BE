package grep.neogulcoder.global.auth.jwt.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccessTokenDto {

    private String jti;
    private String token;
    private Long expires;

}
