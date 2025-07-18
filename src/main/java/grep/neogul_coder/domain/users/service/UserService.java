package grep.neogul_coder.domain.users.service;

import grep.neogul_coder.domain.prtemplate.entity.Link;
import grep.neogul_coder.domain.prtemplate.entity.PrTemplate;
import grep.neogul_coder.domain.prtemplate.repository.LinkRepository;
import grep.neogul_coder.domain.prtemplate.repository.PrTemplateRepository;
import grep.neogul_coder.domain.prtemplate.service.LinkService;
import grep.neogul_coder.domain.prtemplate.service.PrTemplateService;
import grep.neogul_coder.domain.study.service.StudyManagementService;
import grep.neogul_coder.domain.users.controller.dto.request.SignUpRequest;
import grep.neogul_coder.domain.users.controller.dto.response.UserResponse;
import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.domain.users.exception.EmailDuplicationException;
import grep.neogul_coder.domain.users.exception.NicknameDuplicatedException;
import grep.neogul_coder.domain.users.exception.PasswordNotMatchException;
import grep.neogul_coder.domain.users.exception.UserNotFoundException;
import grep.neogul_coder.domain.users.exception.code.UserErrorCode;
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
    private final PrTemplateRepository prTemplateRepository;
    private final PrTemplateService prTemplateService;
    private final LinkRepository linkRepository;
    private final LinkService linkService;
    private final StudyManagementService studyManagementService;


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

        linkRepository.save(Link.LinkInit(user.getId(), null, null));
        linkRepository.save(Link.LinkInit(user.getId(), null, null));
    }

    public void updateProfile(Long id, String nickname, String profileImageUrl) {
        User user = findUser(id);
        isDuplicateNickname(nickname);
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

    public void deleteUser(Long userId, String password) {
        User user = findUser(userId);

        if (isNotMatchCurrentPassword(password, user.getPassword())) {
            throw new PasswordNotMatchException(UserErrorCode.PASSWORD_MISMATCH);
        }

        studyManagementService.deleteUserFromStudies(userId);
        prTemplateService.deleteByUserId(user.getId());
        linkService.deleteByUserId(userId);

        user.delete();
    }

    public void deleteUser(Long userId) {
        User user = findUser(userId);

        prTemplateService.deleteByUserId(user.getId());
        linkService.deleteByUserId(userId);

        user.delete();
    }

    public UserResponse getUserResponse(Long userId) {
        User user = get(userId);
        return UserResponse.toUserResponse(
            user.getId(),
            user.getEmail(),
            user.getNickname(),
            user.getProfileImageUrl(),
            user.getRole());
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
            .orElseThrow(
                () -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));
    }

    private User findUser(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(
                () -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));
    }

    private void duplicationCheck(String email, String nickname) {
        if (isDuplicateEmail(email)) {
            throw new EmailDuplicationException(UserErrorCode.IS_DUPLICATED_MALI);
        }

        if (isDuplicateNickname(nickname)) {
            throw new NicknameDuplicatedException(UserErrorCode.IS_DUPLICATED_NICKNAME);
        }
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




