package grep.neogul_coder.domain.users.controller;

import grep.neogul_coder.domain.users.controller.dto.request.PasswordRequest;
import grep.neogul_coder.domain.users.controller.dto.request.UpdatePasswordRequest;
import grep.neogul_coder.domain.users.controller.dto.request.UpdateProfileRequest;
import grep.neogul_coder.domain.users.controller.dto.response.UserResponse;
import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import grep.neogul_coder.domain.users.controller.dto.request.SignUpRequest;
import grep.neogul_coder.domain.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController implements UserSpecification {

    private final UserService usersService;

    @GetMapping("/me")
    public ApiResponse<UserResponse> get(@AuthenticationPrincipal Principal principal) {
        User user = usersService.get(principal.getUserId());
        UserResponse userResponse =
            UserResponse.toUserResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getRole());
        return ApiResponse.success(userResponse);
    }

    @PutMapping("/update/profile")
    public ApiResponse<Void> updateProfile(@AuthenticationPrincipal Principal principal,
        @RequestBody UpdateProfileRequest request) {
        usersService.updateProfile(principal.getUserId(),request.getNickname(),request.getProfileImgUrl());
        return ApiResponse.noContent();
    }

    @PutMapping("/update/password")
    public ApiResponse<Void> updatePassword(@AuthenticationPrincipal Principal principal,
        @Valid @RequestBody UpdatePasswordRequest request) {
        usersService.updatePassword(principal.getUserId(),request.getPassword(),request.getNewPassword(),request.getNewPasswordCheck());
        return ApiResponse.noContent();
    }

    @DeleteMapping("/delete/me")
    public ApiResponse<Void> delete(@AuthenticationPrincipal Principal principal,
        @RequestBody @Valid PasswordRequest request) {
        usersService.deleteUser(principal.getUserId(),request.getPassword());
        return ApiResponse.noContent();
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> signUp(@Valid @RequestBody SignUpRequest request) {
        usersService.signUp(request);
        return ApiResponse.noContent();
    }

}