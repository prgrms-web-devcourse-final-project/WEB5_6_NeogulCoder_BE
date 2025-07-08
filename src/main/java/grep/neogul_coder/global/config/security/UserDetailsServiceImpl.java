package grep.neogul_coder.global.config.security;

import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.users.entity.Users;
import grep.neogul_coder.users.repository.UsersRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;


    @Override
    public UserDetails loadUserByUsername(String email) {
        Users user = usersRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(email));
        List<SimpleGrantedAuthority> authorities = findUserAuthorities(email);
        return Principal.createPrincipal(user, authorities);
    }

    @Cacheable(cacheNames="authority")
    public List<SimpleGrantedAuthority> findUserAuthorities(String email){
        Users user = usersRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(email));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));

        return authorities;
    }
}
