package grep.neogul_coder.users.service;

import grep.neogul_coder.global.auth.code.Role;
import grep.neogul_coder.users.dto.SignUpRequest;
import grep.neogul_coder.users.entity.Users;
import grep.neogul_coder.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(SignUpRequest request) {

        if (isDuplicateEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = encodingPassword(request.getPassword());
        Users newUser = Users.builder()
            .email(request.getEmail())
            .password(encodedPassword)
            .nickname(request.getNickname())
            .isDeleted(false)
            .role(Role.ROLE_USER)
            .build();

        usersRepository.save(newUser);
    }

    private boolean isDuplicateEmail(String email) {
        return usersRepository.findByEmail(email).isPresent();
    }

    private String encodingPassword(String password) {
        return passwordEncoder.encode(password);
    }

}




