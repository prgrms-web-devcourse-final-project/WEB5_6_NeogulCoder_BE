package grep.neogul_coder.domain.users.controller;

import grep.neogul_coder.domain.users.controller.dto.request.PasswordRequest;
import grep.neogul_coder.domain.users.controller.dto.request.SignUpRequest;
import grep.neogul_coder.domain.users.controller.dto.request.UpdatePasswordRequest;
import grep.neogul_coder.domain.users.controller.dto.request.UpdateProfileRequest;
import grep.neogul_coder.domain.users.controller.dto.response.UserResponse;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "User", description = "회원 API")
public interface UserSpecification {

    @Operation(summary = "회원 가입", description = "회원 정보를 저장합니다.")
    ApiResponse<Void> signUp(@RequestBody SignUpRequest request);

    @Operation(summary = "회원 조회", description = "회원 정보를 조회합니다.")
    ApiResponse<UserResponse> get(@PathVariable("id") Long id);

    @Operation(summary = "회원 프로필 수정", description = "회원 프로필을 수정합니다.")
    ApiResponse<Void> updateProfile(@PathVariable("id") Long id,
        @RequestBody UpdateProfileRequest request);

    @Operation(summary = "회원 비밀번호 수정", description = "회원 비밀번호를 수정합니다.")
    ApiResponse<Void> updatePassword(@PathVariable Long id,
        @RequestBody UpdatePasswordRequest request);

    @Operation(summary = "회원 상태 삭제로 변경", description = "회원 상태를 삭제로 변경합니다.")
    ApiResponse<Void> delete(@PathVariable Long id, @RequestBody PasswordRequest request);
}