package grep.neogulcoder.domain.users.controller;

import grep.neogulcoder.domain.users.controller.dto.request.PasswordRequest;
import grep.neogulcoder.domain.users.controller.dto.request.SignUpRequest;
import grep.neogulcoder.domain.users.controller.dto.request.UpdatePasswordRequest;
import grep.neogulcoder.domain.users.controller.dto.response.AllUserResponse;
import grep.neogulcoder.domain.users.controller.dto.response.UserResponse;
import grep.neogulcoder.domain.users.service.EmailVerificationService;
import grep.neogulcoder.domain.users.service.UserService;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController implements UserSpecification {

    private final UserService usersService;
    private final EmailVerificationService verificationService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> get(
        @AuthenticationPrincipal Principal principal) {
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
    public ResponseEntity<ApiResponse<Void>> updatePassword(
        @AuthenticationPrincipal Principal principal,
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
        usersService.signUp(request);
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    @PostMapping("/mail/send")
    public ResponseEntity<ApiResponse<Void>> sendCode(@RequestParam String email) {
        verificationService.sendVerificationEmail(email);
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    @PostMapping("/mail/verify")
    public ResponseEntity<ApiResponse<Void>> verifyCode(
        @RequestParam String email,
        @RequestParam String code
    ) {
        boolean result = verificationService.verifyCode(email, code);
        return result ?
            ResponseEntity.ok(ApiResponse.noContent()) :
            ResponseEntity.ok(ApiResponse.badRequest());
    }

}