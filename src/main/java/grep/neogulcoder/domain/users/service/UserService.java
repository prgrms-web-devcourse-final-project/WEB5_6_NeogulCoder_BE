package grep.neogulcoder.domain.users.service;

import grep.neogulcoder.domain.buddy.service.BuddyEnergyService;
import grep.neogulcoder.domain.prtemplate.entity.Link;
import grep.neogulcoder.domain.prtemplate.entity.PrTemplate;
import grep.neogulcoder.domain.prtemplate.repository.LinkRepository;
import grep.neogulcoder.domain.prtemplate.repository.PrTemplateRepository;
import grep.neogulcoder.domain.prtemplate.service.LinkService;
import grep.neogulcoder.domain.prtemplate.service.PrTemplateService;
import grep.neogulcoder.domain.study.service.StudyManagementService;
import grep.neogulcoder.domain.study.service.StudyManagementServiceFacade;
import grep.neogulcoder.domain.users.controller.dto.request.SignUpRequest;
import grep.neogulcoder.domain.users.controller.dto.response.AllUserResponse;
import grep.neogulcoder.domain.users.controller.dto.response.UserResponse;
import grep.neogulcoder.domain.users.entity.User;
import grep.neogulcoder.domain.users.exception.*;
import grep.neogulcoder.domain.users.exception.code.UserErrorCode;
import grep.neogulcoder.domain.users.repository.UserRepository;
import grep.neogulcoder.global.utils.upload.AbstractFileManager;
import grep.neogulcoder.global.utils.upload.FileUploadResponse;
import grep.neogulcoder.global.utils.upload.FileUsageType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    private final MailService mailService;
    private final AbstractFileManager fileUploader;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PrTemplateRepository prTemplateRepository;
    private final PrTemplateService prTemplateService;
    private final LinkRepository linkRepository;
    private final LinkService linkService;
    private final StudyManagementService studyManagementService;
    private final BuddyEnergyService buddyEnergyService;
    private final EmailVerificationService verificationService;
    private final StudyManagementServiceFacade studyManagementServiceFacade;

    @Transactional(readOnly = true)
    public User get(Long id) {
        return findUserById(id);
    }

    @Transactional(readOnly = true)
    public User getByEmail(String email) {
        return findUserByEmail(email);
    }

    public void signUp(SignUpRequest request) {
        String email = request.getEmail();
        String nickname = request.getNickname();

        if (isDuplicateEmail(email)) {
            throw new EmailDuplicationException(UserErrorCode.IS_DUPLICATED_MALI);
        }

        if (isDuplicateNickname(nickname)) {
            throw new NicknameDuplicatedException(UserErrorCode.IS_DUPLICATED_NICKNAME);
        }

        if(mailService.confirmNotVerifiedEmail(email)){
            throw new NotVerifiedEmailException(UserErrorCode.NOT_VERIFIED_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = userRepository.save(User.create(email, encodedPassword, nickname));
        saveBasicUserData(user.getId());
    }

    public void updateProfile(Long userId, String nickname, MultipartFile profileImage)
        throws IOException {

        User user = findUserById(userId);
        String validatedNickname = validateUpdateNickname(user, nickname);

        String uploadedImageUrl = updateImageUrl(userId, profileImage, user.getProfileImageUrl());

        user.updateProfile(validatedNickname, uploadedImageUrl);
    }

    public void updatePassword(Long id, String password, String newPassword,
        String newPasswordCheck) {
        User user = findUserById(id);

        if (isNotMatchCurrentPassword(password, user.getPassword())) {
            throw new PasswordNotMatchException(UserErrorCode.PASSWORD_MISMATCH);
        }

        if (isNotMatchPassword(newPassword, newPasswordCheck)) {
            throw new PasswordNotMatchException(UserErrorCode.PASSWORD_UNCHECKED);
        }

        String encodedPassword = encodingPassword(newPassword);
        user.updatePassword(encodedPassword);
    }

    public void deleteUser(Long userId, String password, String passwordCheck) {
        User user = findUserById(userId);

        if (isNotMatchCurrentPassword(password, user.getPassword())) {
            throw new PasswordNotMatchException(UserErrorCode.PASSWORD_MISMATCH);
        }

        if(isNotMatchPassword(password, passwordCheck)) {
            throw new PasswordNotMatchException(UserErrorCode.PASSWORD_UNCHECKED);
        }

        studyManagementServiceFacade.deleteUserFromStudiesWithRetry(userId);
        prTemplateService.deleteByUserId(user.getId());
        linkService.deleteByUserId(userId);

        user.delete();
    }

    public void deleteUser(Long userId) {
        User user = findUserById(userId);

        studyManagementService.deleteUserFromStudies(userId);
        prTemplateService.deleteByUserId(user.getId());
        linkService.deleteByUserId(userId);

        user.delete();
    }

    public void saveBasicUserData(Long userId) {
        prTemplateRepository.save(PrTemplate.PrTemplateInit(userId, null, null));
        linkRepository.save(Link.LinkInit(userId, null, null));
        linkRepository.save(Link.LinkInit(userId, null, null));
        buddyEnergyService.createDefaultEnergy(userId);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserResponse(Long userId) {
        User user = get(userId);
        return UserResponse.toUserResponse(
            user.getId(),
            user.getEmail(),
            user.getNickname(),
            user.getProfileImageUrl(),
            user.getOauthProvider(),
            user.getRole());
    }

    @Transactional(readOnly = true)
    public List<AllUserResponse> getAllUsers() {
        return userRepository.findAllByActivatedTrue().stream()
            .map(user -> AllUserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .build()
            )
            .toList();
    }

    public void reactiveUser(Long userId) {
        User user = findUserById(userId);
        user.reactive();
    }

    private String validateUpdateNickname(User user, String nickname) {
        if (isChangedNickname(nickname)) {
            nickNameDuplicationCheck(nickname);
        } else {
            nickname = user.getNickname();
        }
        return nickname;
    }

    private boolean isChangedNickname(String nickname){
        return nickname != null && !nickname.isBlank();
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(
                () -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(
                () -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));
    }

    private void nickNameDuplicationCheck(String nickname){
        if(isDuplicateNickname(nickname)){
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

    private boolean isNotMatchPassword(String password, String passwordCheck) {
        return !password.equals(passwordCheck);
    }

    private String updateImageUrl(Long userId, MultipartFile image, String originalImageUrl) throws IOException {
        if (isImgExists(image)) {
            FileUploadResponse response = fileUploader.upload(image, userId, FileUsageType.PROFILE, userId);
            return response.getFileUrl();
        }
        return originalImageUrl;
    }

    private boolean isImgExists(MultipartFile image) {
        return image != null && !image.isEmpty();
    }
}
