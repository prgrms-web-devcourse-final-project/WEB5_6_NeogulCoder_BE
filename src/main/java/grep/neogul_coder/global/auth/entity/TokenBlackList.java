package grep.neogul_coder.global.auth.entity;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash("tokenBlackList")
public class TokenBlackList {

    @Id
    private String id;
    private String email;
    private String tokenId;
    private LocalDateTime createdAt = LocalDateTime.now();

    public TokenBlackList(String email, String tokenId) {
        this.email = email;
        this.tokenId = tokenId;
    }
}
