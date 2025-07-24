package grep.neogulcoder.global.auth;

import grep.neogulcoder.domain.users.entity.User;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
public class Principal extends org.springframework.security.core.userdetails.User {

    private final long userId;
    private String accessToken;

    public Principal(long userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
    }

    public static Principal createPrincipal(User users, List<SimpleGrantedAuthority> authorities){
        return new Principal(users.getId(), users.getEmail(), users.getPassword(), authorities);
    }

    public Optional<String> getAccessToken() {
        return Optional.of(accessToken);
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}