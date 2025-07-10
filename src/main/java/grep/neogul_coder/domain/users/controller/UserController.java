package grep.neogul_coder.domain.users.controller;

import grep.neogul_coder.domain.users.controller.dto.UpdatePasswordRequest;
import grep.neogul_coder.domain.users.controller.dto.UpdateProfileRequest;
import grep.neogul_coder.global.response.ApiResponse;
import grep.neogul_coder.domain.users.controller.dto.SignUpRequest;
import grep.neogul_coder.domain.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService usersService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> signUp(@RequestBody SignUpRequest request) {
        usersService.signUp(request);
        return ApiResponse.noContent();
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> updateProfile(@PathVariable Long id,
        @RequestBody UpdateProfileRequest request) {
        usersService.updateProfile(id,request.getNickname(),request.getProfileImgUrl());
        return ApiResponse.noContent();
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> updatePassword(@PathVariable Long id,
        @RequestBody UpdatePasswordRequest request) {
        usersService.updatePassword(id,request.getPassword(),request.getNewPassword(),request.getNewPasswordCheck());
        return ApiResponse.noContent();
    }
}