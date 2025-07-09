package grep.neogul_coder.global.auth;

import grep.neogul_coder.users.entity.User;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class Principal extends org.springframework.security.core.userdetails.User {

    private String accessToken;

    public Principal(String username, String password,
        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public static Principal createPrincipal(User users, List<SimpleGrantedAuthority> authorities){
        return new Principal(users.getEmail(), users.getPassword(), authorities);
    }

    public Optional<String> getAccessToken() {
        return Optional.of(accessToken);
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}