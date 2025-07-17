package grep.neogul_coder.global.auth.controller;

import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.domain.users.service.UserService;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.auth.code.AuthToken;
import grep.neogul_coder.global.auth.jwt.JwtTokenProvider;
import grep.neogul_coder.global.auth.jwt.TokenCookieFactory;
import grep.neogul_coder.global.auth.jwt.dto.TokenDto;
import grep.neogul_coder.global.auth.payload.LoginRequest;
import grep.neogul_coder.global.auth.payload.LoginResponse;
import grep.neogul_coder.global.auth.payload.LoginUserResponse;
import grep.neogul_coder.global.auth.payload.TokenResponse;
import grep.neogul_coder.global.auth.service.AuthService;
import grep.neogul_coder.global.auth.service.RefreshTokenService;
import grep.neogul_coder.global.response.ApiResponse;
import grep.neogul_coder.global.response.code.CommonCode;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
        @RequestBody LoginRequest loginRequest,
        HttpServletResponse response
    ) {
        TokenDto tokenDto = authService.signin(loginRequest);
        User user = userService.getByEmail(loginRequest.getEmail());

        ResponseCookie accessToken = TokenCookieFactory.create(AuthToken.ACCESS_TOKEN.name(),
            tokenDto.getAccessToken(), tokenDto.getExpiresIn());
        ResponseCookie refreshToken = TokenCookieFactory.create(AuthToken.REFRESH_TOKEN.name(),
            tokenDto.getRefreshToken(), tokenDto.getExpiresIn());

        response.addHeader("Set-Cookie", accessToken.toString());
        response.addHeader("Set-Cookie", refreshToken.toString());

        return ApiResponse.success(LoginResponse.builder()
            .token(TokenResponse.builder()
                .accessToken(tokenDto.getAccessToken())
                .grantType(tokenDto.getGrantType())
                .expiresIn(tokenDto.getExpiresIn())
                .build())
            .user(LoginUserResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImage(user.getProfileImageUrl())
                .role(user.getRole())
                .build())
            .build());
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = jwtTokenProvider.resolveToken(request, AuthToken.ACCESS_TOKEN);

        if (accessToken == null || accessToken.isEmpty()) {
            return ApiResponse.errorWithoutData(CommonCode.BAD_REQUEST);
        }

        try {

            Claims claims = jwtTokenProvider.getClaims(accessToken);
            refreshTokenService.deleteByAccessTokenId(claims.getId());

            SecurityContextHolder.clearContext();

            ResponseCookie expiredAccessToken = TokenCookieFactory.createExpiredToken(
                AuthToken.ACCESS_TOKEN.name());
            ResponseCookie expiredRefreshToken = TokenCookieFactory.createExpiredToken(
                AuthToken.REFRESH_TOKEN.name());
            ResponseCookie expiredSessionId = TokenCookieFactory.createExpiredToken(
                AuthToken.AUTH_SERVER_SESSION_ID.name());

            response.addHeader("Set-Cookie", expiredAccessToken.toString());
            response.addHeader("Set-Cookie", expiredRefreshToken.toString());
            response.addHeader("Set-Cookie", expiredSessionId.toString());

            return ApiResponse.noContent();

        } catch (Exception e) {
            return ApiResponse.errorWithoutData(CommonCode.BAD_REQUEST);
        }
    }

}