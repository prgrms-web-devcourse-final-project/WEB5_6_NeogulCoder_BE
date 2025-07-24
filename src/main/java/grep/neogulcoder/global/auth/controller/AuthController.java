package grep.neogulcoder.global.auth.controller;

import grep.neogulcoder.domain.users.entity.User;
import grep.neogulcoder.domain.users.service.UserService;
import grep.neogulcoder.global.auth.code.AuthToken;
import grep.neogulcoder.global.auth.jwt.JwtTokenProvider;
import grep.neogulcoder.global.auth.jwt.TokenCookieFactory;
import grep.neogulcoder.global.auth.jwt.dto.TokenDto;
import grep.neogulcoder.global.auth.payload.LoginRequest;
import grep.neogulcoder.global.auth.payload.LoginResponse;
import grep.neogulcoder.global.auth.payload.LoginUserResponse;
import grep.neogulcoder.global.auth.payload.TokenResponse;
import grep.neogulcoder.global.auth.service.AuthService;
import grep.neogulcoder.global.auth.service.RefreshTokenService;
import grep.neogulcoder.global.response.ApiResponse;
import grep.neogulcoder.global.response.code.CommonCode;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
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