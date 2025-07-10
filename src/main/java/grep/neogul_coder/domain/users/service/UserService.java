package grep.neogul_coder.domain.users.service;

import grep.neogul_coder.domain.users.controller.dto.SignUpRequest;
import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.domain.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(SignUpRequest request) {

        if (isDuplicateEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        if (isNotMatchPasswordCheck(request.getPassword(), request.getPasswordCheck())) {
            throw new IllegalArgumentException("비밀번호 확인이 일치하지 않습니다.");
        }

        if (isDuplicateNickname(request.getNickname())) {
            throw new IllegalArgumentException("동일한 닉네임이 존재합니다.");
        }

        String encodedPassword = encodingPassword(request.getPassword());
        userRepository.save(
            User.UserInit(request.getEmail(), encodedPassword, request.getNickname()));
    }

    public void updateProfile(Long id, String nickname, String profileImageUrl) {
        User user = findUser(id);
        user.updateProfile(nickname, profileImageUrl);
    }

    public void updatePassword(Long id, String password, String newPassword,
        String newPasswordCheck) {
        User user = findUser(id);

        if (isNotMatchCurrentPassword(password, user.getPassword())) {
            throw new RuntimeException("비밀번호를 다시 확인해주세요");
        }

        if (isNotMatchPasswordCheck(newPassword, newPasswordCheck)) {
            throw new IllegalArgumentException("비밀번호 확인이 일치하지 않습니다.");
        }

        String encodedPassword = encodingPassword(newPassword);

        user.updatePassword(encodedPassword);
    }

    public void deleteUser(Long id, String password) {

        User user = findUser(id);

        if (isNotMatchCurrentPassword(password, user.getPassword())) {
            throw new RuntimeException("비밀번호를 다시 확인해주세요");
        }

        user.delete();
    }

    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));
    }

    private boolean isDuplicateEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private boolean isDuplicateNickname(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    private String encodingPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private boolean isNotMatchCurrentPassword(String inputPassword, String storedPassword) {
        return !passwordEncoder.matches(inputPassword, storedPassword);
    }

    private boolean isNotMatchPasswordCheck(String password, String passwordCheck) {
        return !password.equals(passwordCheck);
    }
}




