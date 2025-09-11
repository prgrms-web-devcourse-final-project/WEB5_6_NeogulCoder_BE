package grep.neogulcoder.domain.users.controller;

import grep.neogulcoder.domain.users.controller.dto.request.*;
import grep.neogulcoder.domain.users.controller.dto.response.AllUserResponse;
import grep.neogulcoder.domain.users.controller.dto.response.UserResponse;
import grep.neogulcoder.domain.users.exception.PasswordNotMatchException;
import grep.neogulcoder.domain.users.exception.code.UserErrorCode;
import grep.neogulcoder.domain.users.service.MailService;
import grep.neogulcoder.domain.users.service.UserService;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController implements UserSpecification {

    private final UserService usersService;
    private final MailService mailService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> get(@AuthenticationPrincipal Principal principal) {
        UserResponse userResponse = usersService.getUserResponse(principal.getUserId());
        return ResponseEntity.ok(ApiResponse.success(userResponse));
    }

    @GetMapping("/{userid}")
    public ResponseEntity<ApiResponse<UserResponse>> get(@PathVariable("userid") Long userId) {
        UserResponse userResponse = usersService.getUserResponse(userId);
        return ResponseEntity.ok(ApiResponse.success(userResponse));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<AllUserResponse>>> getAll() {
        List<AllUserResponse> users = usersService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @PutMapping(value = "/update/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Void>> updateProfile(
            @AuthenticationPrincipal Principal principal,
            @RequestParam(value = "nickname", required = false) String nickname,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage
    ) throws IOException {
        usersService.updateProfile(principal.getUserId(), nickname, profileImage);
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    @PutMapping("/update/password")
    public ResponseEntity<ApiResponse<Void>> updatePassword(@AuthenticationPrincipal Principal principal,
                                                            @Valid @RequestBody UpdatePasswordRequest request) {
        usersService.updatePassword(principal.getUserId(), request.getPassword(),
                request.getNewPassword(), request.getNewPasswordCheck());
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    @DeleteMapping("/delete/me")
    public ResponseEntity<ApiResponse<Void>> delete(@AuthenticationPrincipal Principal principal,
                                                    @RequestBody @Valid PasswordRequest request) {
        usersService.deleteUser(principal.getUserId(), request.getPassword(), request.getPasswordCheck());
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<Void>> signUp(@Valid @RequestBody SignUpRequest request) {
        if (isNotMatchPassword(request.getPassword(), request.getPasswordCheck())) {
            throw new PasswordNotMatchException(UserErrorCode.PASSWORD_MISMATCH);
        }

        usersService.signUp(request);
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    @PostMapping("/mail/send")
    public ResponseEntity<ApiResponse<Void>> sendCode(@Valid @RequestBody SendCodeToEmailRequest request) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        mailService.sendCodeTo(request.getEmail(), currentDateTime);
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    @PostMapping("/mail/verify")
    public ResponseEntity<ApiResponse<Boolean>> verifyCode(@Valid @RequestBody EmailVerifyRequest request) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        boolean verified = mailService.verifyEmailCode(request, currentDateTime);
        return ResponseEntity.ok(ApiResponse.success("인증 성공 여부", verified));
    }

    private boolean isNotMatchPassword(String password, String passwordCheck) {
        return !password.equals(passwordCheck);
    }
}