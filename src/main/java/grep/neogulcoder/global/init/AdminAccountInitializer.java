package grep.neogulcoder.global.init;

import grep.neogulcoder.domain.users.entity.User;
import grep.neogulcoder.domain.users.repository.UserRepository;
import grep.neogulcoder.global.auth.code.Role;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminAccountInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initAdminAccount() {
        String adminEmail = "admin@admin.com";

        if (userRepository.findByEmail(adminEmail).isPresent()) {
            return;
        }

        User admin = User.builder()
            .email(adminEmail)
            .password(passwordEncoder.encode("admin1234"))
            .nickname("관리자")
            .role(Role.ROLE_ADMIN)
            .build();

        userRepository.save(admin);

    }
}
