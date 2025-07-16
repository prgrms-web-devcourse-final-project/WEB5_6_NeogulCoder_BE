package grep.neogul_coder.domain.users.service;

import grep.neogul_coder.domain.prtemplate.entity.PrTemplate;
import grep.neogul_coder.domain.prtemplate.repository.LinkRepository;
import grep.neogul_coder.domain.prtemplate.repository.PrTemplateRepository;
import grep.neogul_coder.domain.prtemplate.service.LinkService;
import grep.neogul_coder.domain.prtemplate.service.PrTemplateService;
import grep.neogul_coder.domain.users.controller.dto.request.SignUpRequest;
import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.domain.users.exception.EmailDuplicationException;
import grep.neogul_coder.domain.users.exception.EmailDuplicationException;
import grep.neogul_coder.domain.users.exception.PasswordNotMatchException;
import grep.neogul_coder.domain.users.exception.UserNotFoundException;
import grep.neogul_coder.domain.users.exception.code.UserErrorCode;
import grep.neogul_coder.domain.users.repository.UserRepository;
import grep.neogul_coder.global.exception.business.NotFoundException;
import grep.neogul_coder.global.exception.validation.DuplicatedException;
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
    private final PrTemplateRepository prTemplateRepository;
    private final PrTemplateService prTemplateService;
    private final LinkRepository linkRepository;
    private final LinkService linkService;

    public User get(Long id) {
        User user = findUser(id);
        return user;
    }

    public User getByEmail(String email) {
        User user = findUser(email);
        return user;
    }

    public void signUp(SignUpRequest request) {

        duplicationCheck(request.getEmail(), request.getNickname());

        if (isNotMatchPasswordCheck(request.getPassword(), request.getPasswordCheck())) {
            throw new PasswordNotMatchException(UserErrorCode.PASSWORD_MISMATCH);
        }

        String encodedPassword = encodingPassword(request.getPassword());
        userRepository.save(
            User.UserInit(request.getEmail(), encodedPassword, request.getNickname()));

        User user = findUser(request.getEmail());

        prTemplateRepository.save(
            PrTemplate.PrTemplateInit(user.getId(), null, null));
    }

    public void updateProfile(Long id, String nickname, String profileImageUrl) {
        User user = findUser(id);
        user.updateProfile(nickname, profileImageUrl);
    }

    public void updatePassword(Long id, String password, String newPassword,
        String newPasswordCheck) {
        User user = findUser(id);

        if (isNotMatchCurrentPassword(password, user.getPassword())) {
            throw new PasswordNotMatchException(UserErrorCode.PASSWORD_MISMATCH);
        }

        if (isNotMatchPasswordCheck(newPassword, newPasswordCheck)) {
            throw new PasswordNotMatchException(UserErrorCode.PASSWORD_UNCHECKED);
        }

        String encodedPassword = encodingPassword(newPassword);
        user.updatePassword(encodedPassword);
    }

    public void deleteUser(Long id, String password) {
        User user = findUser(id);

        if (isNotMatchCurrentPassword(password, user.getPassword())) {
            throw new PasswordNotMatchException(UserErrorCode.PASSWORD_MISMATCH);
        }

        prTemplateService.deleteByUserId(user.getId());
        linkService.deleteByPrId(prTemplateRepository.findByUserId((user.getId())).getId());

        user.delete();
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
            .orElseThrow(
                () -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND, "회원이 존재하지 않습니다."));
    }

    private User findUser(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(
                () -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND, "회원이 존재하지 않습니다."));
    }

    private boolean duplicationCheck(String email, String nickname) {
        if (isDuplicateEmail(email)) {
            throw new EmailDuplicationException(UserErrorCode.IS_DUPLICATED_MALI);
        }

        if (isDuplicateNickname(nickname)) {
            throw new DuplicatedException(UserErrorCode.IS_DUPLICATED_NICKNAME);
        }
        return false;
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




