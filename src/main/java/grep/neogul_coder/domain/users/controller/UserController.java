package grep.neogul_coder.domain.users.controller;

import grep.neogul_coder.domain.users.controller.dto.request.PasswordRequest;
import grep.neogul_coder.domain.users.controller.dto.request.SignUpRequest;
import grep.neogul_coder.domain.users.controller.dto.request.UpdatePasswordRequest;
import grep.neogul_coder.domain.users.controller.dto.response.UserResponse;
import grep.neogul_coder.domain.users.service.UserService;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController implements UserSpecification {

    private final UserService usersService;

    @GetMapping("/me")
    public ApiResponse<UserResponse> get(@AuthenticationPrincipal Principal principal) {
        UserResponse userResponse = usersService.getUserResponse(principal.getUserId());
        return ApiResponse.success(userResponse);
    }

    @GetMapping("/{userid}")
    public ApiResponse<UserResponse> get(@PathVariable("userid") Long userId) {
        UserResponse userResponse = usersService.getUserResponse(userId);
        return ApiResponse.success(userResponse);
    }

    @PutMapping(value = "/update/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Void> updateProfile(
            @AuthenticationPrincipal Principal principal,
            @RequestPart("nickname") String nickname,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage
    ) throws IOException {
        usersService.updateProfile(principal.getUserId(), nickname, profileImage);
        return ApiResponse.noContent();
    }

    @PutMapping("/update/password")
    public ApiResponse<Void> updatePassword(@AuthenticationPrincipal Principal principal,
                                            @Valid @RequestBody UpdatePasswordRequest request) {
        usersService.updatePassword(principal.getUserId(), request.getPassword(), request.getNewPassword(), request.getNewPasswordCheck());
        return ApiResponse.noContent();
    }

    @DeleteMapping("/delete/me")
    public ApiResponse<Void> delete(@AuthenticationPrincipal Principal principal,
                                    @RequestBody @Valid PasswordRequest request) {
        usersService.deleteUser(principal.getUserId(), request.getPassword());
        return ApiResponse.noContent();
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> signUp(@Valid @RequestBody SignUpRequest request) {
        usersService.signUp(request);
        return ApiResponse.noContent();
    }

}