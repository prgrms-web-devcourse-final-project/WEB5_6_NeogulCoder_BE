package grep.neogul_coder.domain.users.controller;

import grep.neogul_coder.domain.users.controller.dto.SignUpRequest;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User", description = "회원 API")
public interface UserSpecification {

    @Operation(summary = "회원 가입", description = "회원 정보를 저장합니다.")
    ApiResponse<Void> signUp(SignUpRequest request);

}