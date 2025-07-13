package grep.neogul_coder.global.auth.jwt;

import grep.neogul_coder.global.auth.code.AuthToken;
import grep.neogul_coder.global.auth.entity.RefreshToken;
import grep.neogul_coder.global.auth.entity.UserBlackList;
import grep.neogul_coder.global.auth.jwt.dto.AccessTokenDto;
import grep.neogul_coder.global.auth.repository.UserBlackListRepository;
import grep.neogul_coder.global.auth.service.RefreshTokenService;
import grep.neogul_coder.global.exception.CommonException;
import grep.neogul_coder.global.response.CommonCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final RefreshTokenService refreshTokenService;
    private final UserBlackListRepository userBlackListRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        List<String> excludePath = new ArrayList<>();
        excludePath.addAll(List.of("/auth/signup", "/auth/login",  "/favicon.ico", "/img", "/js","/css","/download"));
        excludePath.addAll(List.of("/error", "/api/member/exists", "/member/signin", "/member/signup"));
        String path = request.getRequestURI();
        return excludePath.stream().anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtTokenProvider.resolveToken(request, AuthToken.ACCESS_TOKEN);
        if (accessToken == null || accessToken.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if (jwtTokenProvider.validateToken(accessToken, request)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                if (userBlackListRepository.existsById(authentication.getName())) {
                    filterChain.doFilter(request, response);
                    return;
                }
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException e) {
            manageTokenRefresh(accessToken, request, response);
        }
        filterChain.doFilter(request, response);
    }

    private void manageTokenRefresh(
        String accessToken,
        HttpServletRequest request,
        HttpServletResponse response) throws IOException {

        Claims claims  = jwtTokenProvider.getClaims(accessToken);
        if (userBlackListRepository.existsById(claims.getSubject())) {
            return;
        }

        String refreshToken = jwtTokenProvider.resolveToken(request, AuthToken.REFRESH_TOKEN);
        RefreshToken rt = refreshTokenService.findByAccessTokenId(claims.getId());

        if(rt == null) return;

        if (!rt.getToken().equals(refreshToken)) {
            userBlackListRepository.save(new UserBlackList(claims.getSubject()));
            throw new CommonException(CommonCode.SECURITY_INCIDENT);
        }

        addToken(response, claims, rt);
    }

    private void addToken(HttpServletResponse response, Claims claims, RefreshToken refreshToken) {
        String username = claims.getSubject();
        AccessTokenDto newAccessToken = jwtTokenProvider.generateAccessToken(username,
            (String) claims.get("roles"));
        Authentication authentication = jwtTokenProvider.getAuthentication(newAccessToken.getToken());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // refreshToken 수정, 만료일자는 그대로 유지
        RefreshToken newRefreshToken = refreshTokenService.renewingToken(refreshToken.getAtId(), newAccessToken.getJti());

        ResponseCookie accessTokenCookie = TokenCookieFactory.create(AuthToken.ACCESS_TOKEN.name(),
            newAccessToken.getToken(), jwtTokenProvider.getAccessTokenExpiration());

        ResponseCookie refreshTokenCookie = TokenCookieFactory.create(
            AuthToken.REFRESH_TOKEN.name(),
            newRefreshToken.getToken(),
            newRefreshToken.getTtl());

        response.addHeader("Set-Cookie", accessTokenCookie.toString());
        response.addHeader("Set-Cookie", refreshTokenCookie.toString());
    }
}
