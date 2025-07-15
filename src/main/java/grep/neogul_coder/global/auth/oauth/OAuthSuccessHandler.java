package grep.neogul_coder.global.auth.oauth;

import grep.neogul_coder.global.auth.code.AuthToken;
import grep.neogul_coder.global.auth.jwt.JwtTokenProvider;
import grep.neogul_coder.global.auth.jwt.TokenCookieFactory;
import grep.neogul_coder.global.auth.jwt.dto.TokenDto;
import grep.neogul_coder.global.auth.oauth.user.OAuth2UserInfo;
import grep.neogul_coder.global.auth.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        OAuth2UserInfo userInfo = OAuth2UserInfo.createUserInfo(request.getRequestURI(), user);

        String roles = String.join(",", authentication.getAuthorities().stream().map(
            GrantedAuthority::getAuthority).toList());

        TokenDto tokenDto = authService.processOAuthSignin(userInfo, roles);
        ResponseCookie accessTokenCookie = TokenCookieFactory.create(AuthToken.ACCESS_TOKEN.name(),
            tokenDto.getAccessToken(), jwtTokenProvider.getAccessTokenExpiration());

        ResponseCookie refreshTokenCookie = TokenCookieFactory.create(
            AuthToken.REFRESH_TOKEN.name(),
            tokenDto.getRefreshToken(), tokenDto.getRefreshExpiresIn());

        response.addHeader("Set-Cookie", accessTokenCookie.toString());
        response.addHeader("Set-Cookie", refreshTokenCookie.toString());
        getRedirectStrategy().sendRedirect(request, response, "http://localhost:3000/");
    }

}