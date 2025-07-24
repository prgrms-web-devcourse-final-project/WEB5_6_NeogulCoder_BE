package grep.neogulcoder.domain.users.controller;

import grep.neogulcoder.domain.users.controller.dto.request.PasswordRequest;
import grep.neogulcoder.domain.users.controller.dto.request.SignUpRequest;
import grep.neogulcoder.domain.users.controller.dto.request.UpdatePasswordRequest;
import grep.neogulcoder.domain.users.controller.dto.response.UserResponse;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "User", description = "회원 API")
public interface UserSpecification {

    @Operation(summary = "회원 가입", description = "회원 정보를 저장합니다.")
    ApiResponse<Void> signUp(@RequestBody SignUpRequest request);

    @Operation(summary = "회원 조회", description = "회원 정보를 조회합니다.")
    ApiResponse<UserResponse> get(@AuthenticationPrincipal Principal principal);

    @Operation(summary = "회원 프로필 수정", description = "회원 프로필을 수정합니다.")
    ApiResponse<Void> updateProfile(@AuthenticationPrincipal Principal principal,
        @RequestPart("nickname") String nickname,
        @RequestPart(value = "profileImage", required = false) MultipartFile profileImage)
        throws IOException;

    @Operation(summary = "회원 비밀번호 수정", description = "회원 비밀번호를 수정합니다.")
    ApiResponse<Void> updatePassword(@AuthenticationPrincipal Principal principal,
        @RequestBody UpdatePasswordRequest request);

    @Operation(summary = "회원 상태 삭제로 변경", description = "회원 상태를 삭제로 변경합니다.")
    ApiResponse<Void> delete(@AuthenticationPrincipal Principal principal,
        @RequestBody PasswordRequest request);

    @Operation(summary = "이메일 인증 코드 발송", description = "입력한 이메일 주소로 인증 코드를 발송합니다.")
    ApiResponse<Void> sendCode(
        @Parameter(description = "인증 코드를 보낼 이메일 주소", required = true, example = "user@example.com")
        @RequestParam String email
    );

    @Operation(summary = "이메일 인증 코드 검증", description = "사용자가 입력한 인증 코드가 올바른지 검증합니다.")
    ApiResponse<Void> verifyCode(
        @Parameter(description = "인증 요청한 이메일 주소", required = true, example = "user@example.com")
        @RequestParam String email,

        @Parameter(description = "사용자가 입력한 인증 코드", required = true, example = "123456")
        @RequestParam String code
    );
}