package grep.neogul_coder.domain.users.service;

import grep.neogul_coder.global.auth.code.Role;
import grep.neogul_coder.domain.users.controller.dto.SignUpRequest;
import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.domain.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(SignUpRequest request) {

        if (isDuplicateEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        if(!isMatchPassword(request.getPassword(), request.getPasswordCheck())){
            throw new IllegalArgumentException("비밀번호 확인이 일치하지 않습니다.");
        }

        String encodedPassword = encodingPassword(request.getPassword());
        User newUser = User.builder()
            .email(request.getEmail())
            .password(encodedPassword)
            .nickname(request.getNickname())
            .isDeleted(false)
            .role(Role.ROLE_USER)
            .build();

        userRepository.save(newUser);
    }

    private boolean isDuplicateEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private String encodingPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private boolean isMatchPassword(String password, String passwordCheck) {
        return passwordEncoder.matches(password, passwordCheck);
    }

}




