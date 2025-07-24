package grep.neogulcoder.global.config.security;

import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.domain.users.entity.User;
import grep.neogulcoder.domain.users.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = usersRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(email));
        List<SimpleGrantedAuthority> authorities = findUserAuthorities(email);
        return Principal.createPrincipal(user, authorities);
    }

    @Cacheable(cacheNames="authority")
    public List<SimpleGrantedAuthority> findUserAuthorities(String email){
        User user = usersRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(email));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));

        return authorities;
    }
}
