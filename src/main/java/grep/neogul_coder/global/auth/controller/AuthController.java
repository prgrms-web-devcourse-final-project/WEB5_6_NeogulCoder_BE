package grep.neogul_coder.global.auth.controller;

import grep.neogul_coder.global.auth.code.AuthToken;
import grep.neogul_coder.global.auth.jwt.TokenCookieFactory;
import grep.neogul_coder.global.auth.jwt.dto.TokenDto;
import grep.neogul_coder.global.auth.payload.LoginRequest;
import grep.neogul_coder.global.auth.payload.TokenResponse;
import grep.neogul_coder.global.auth.service.AuthService;
import grep.neogul_coder.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
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

    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(
        @RequestBody LoginRequest loginRequest,
        HttpServletResponse response
    ) {
        TokenDto tokenDto = authService.signin(loginRequest);

        ResponseCookie accessToken = TokenCookieFactory.create(AuthToken.ACCESS_TOKEN.name(),
            tokenDto.getAccessToken(), tokenDto.getExpiresIn());
        ResponseCookie refreshToken = TokenCookieFactory.create(AuthToken.REFRESH_TOKEN.name(),
            tokenDto.getRefreshToken(), tokenDto.getExpiresIn());

        response.addHeader("Set-Cookie", accessToken.toString());
        response.addHeader("Set-Cookie", refreshToken.toString());

        return ApiResponse.success(TokenResponse.builder()
            .accessToken(tokenDto.getAccessToken())
            .grantType(tokenDto.getGrantType())
            .expiresIn(tokenDto.getExpiresIn())
            .build());
    }

}