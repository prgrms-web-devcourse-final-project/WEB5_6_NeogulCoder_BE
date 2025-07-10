package grep.neogul_coder.domain.users.controller;

import grep.neogul_coder.domain.users.controller.dto.SignUpRequest;
import grep.neogul_coder.domain.users.controller.dto.UpdatePasswordRequest;
import grep.neogul_coder.domain.users.controller.dto.UpdateProfileRequest;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "User", description = "회원 API")
public interface UserSpecification {

    @Operation(summary = "회원 가입", description = "회원 정보를 저장합니다.")
    ApiResponse<Void> signUp(@RequestBody SignUpRequest request);

    @Operation(summary = "회원 프로필 수정", description = "회원 프로필을 수정합니다.")
    ApiResponse<Void> updateProfile(@PathVariable Long id,@RequestBody UpdateProfileRequest request);

    @Operation(summary = "회원 비밀번호 수정", description = "회원 비밀번호를 수정합니다.")
    ApiResponse<Void> updatePassword(@PathVariable Long id, @RequestBody UpdatePasswordRequest request);
}