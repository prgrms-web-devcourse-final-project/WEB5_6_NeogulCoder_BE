package grep.neogul_coder.global.auth.entity;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash("UserBlackList")
public class UserBlackList {

    @Id
    private String email;
    private LocalDateTime createdAt = LocalDateTime.now();

    public UserBlackList(String email) {
        this.email = email;
    }

}
