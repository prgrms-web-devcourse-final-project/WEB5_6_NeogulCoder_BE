package grep.neogul_coder.domain.users.controller;

import grep.neogul_coder.domain.users.controller.dto.request.PasswordRequest;
import grep.neogul_coder.domain.users.controller.dto.request.UpdatePasswordRequest;
import grep.neogul_coder.domain.users.controller.dto.request.UpdateProfileRequest;
import grep.neogul_coder.domain.users.controller.dto.response.UserResponse;
import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.global.response.ApiResponse;
import grep.neogul_coder.domain.users.controller.dto.request.SignUpRequest;
import grep.neogul_coder.domain.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> get(@PathVariable("id")Long id) {
        User user = usersService.get(id);
        UserResponse userResponse =
            UserResponse.toUserResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getProfileImageUrl());
        return ApiResponse.success(userResponse);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> signUp(@Valid @RequestBody SignUpRequest request) {
        usersService.signUp(request);
        return ApiResponse.noContent();
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> updateProfile(@PathVariable("id") Long id,
        @RequestBody UpdateProfileRequest request) {
        usersService.updateProfile(id,request.getNickname(),request.getProfileImgUrl());
        return ApiResponse.noContent();
    }

    @PutMapping("/password/{id}")
    public ApiResponse<Void> updatePassword(@PathVariable("id") Long id,
        @Valid @RequestBody UpdatePasswordRequest request) {
        usersService.updatePassword(id,request.getPassword(),request.getNewPassword(),request.getNewPasswordCheck());
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable("id") Long id,
        @Valid @RequestParam PasswordRequest request) {
        usersService.deleteUser(id,request.getPassword());
        return ApiResponse.noContent();
    }

}