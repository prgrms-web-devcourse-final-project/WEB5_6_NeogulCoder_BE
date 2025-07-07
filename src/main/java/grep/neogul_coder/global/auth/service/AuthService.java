package grep.neogul_coder.global.auth.service;

import grep.neogul_coder.global.auth.entity.RefreshToken;
import grep.neogul_coder.global.auth.jwt.JwtTokenProvider;
import grep.neogul_coder.global.auth.jwt.dto.AccessTokenDto;
import grep.neogul_coder.global.auth.jwt.dto.TokenDto;
import grep.neogul_coder.global.auth.payload.LoginRequest;
import grep.neogul_coder.global.auth.repository.UserBlackListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserBlackListRepository userBlackListRepository;

    public TokenDto signin(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                loginRequest.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject()
            .authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String roles = String.join(",", authentication.getAuthorities().stream().map(
            GrantedAuthority::getAuthority).toList());
        return processTokenSignin(authentication.getName(), roles);
    }

    private TokenDto processTokenSignin(String email, String roles) {

        userBlackListRepository.deleteByEmail(email);

        AccessTokenDto accessToken = jwtTokenProvider.generateAccessToken(email, roles);
        RefreshToken refreshToken = refreshTokenService.saveWithAtId(accessToken.getJti());

        return TokenDto.builder()
            .atId(accessToken.getJti())
            .refreshToken(refreshToken.getToken())
            .grantType("Bearer")
            .refreshExpiresIn(jwtTokenProvider.getRefreshTokenExpiration())
            .expiresIn(jwtTokenProvider.getAccessTokenExpiration())
            .build();
    }

}
