package grep.neogul_coder.global.auth.service;

import grep.neogul_coder.global.auth.code.Role;
import grep.neogul_coder.global.auth.entity.RefreshToken;
import grep.neogul_coder.global.auth.jwt.JwtTokenProvider;
import grep.neogul_coder.global.auth.jwt.dto.AccessTokenDto;
import grep.neogul_coder.global.auth.jwt.dto.TokenDto;
import grep.neogul_coder.global.auth.oauth.user.OAuth2UserInfo;
import grep.neogul_coder.global.auth.payload.LoginRequest;
import grep.neogul_coder.global.auth.repository.UserBlackListRepository;
import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.domain.users.repository.UserRepository;
import grep.neogul_coder.global.exception.GoogleUserLoginException;
import grep.neogul_coder.global.response.ResponseCode;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final UserRepository usersRepository;

    public TokenDto signin(LoginRequest loginRequest) {

        if(isGoogleUser(loginRequest.getEmail())){
            throw new GoogleUserLoginException(ResponseCode.SECURITY_INCIDENT);
        }

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

    public TokenDto processTokenSignin(String email, String roles) {

        userBlackListRepository.deleteByEmail(email);

        AccessTokenDto accessToken = jwtTokenProvider.generateAccessToken(email, roles);
        RefreshToken refreshToken = refreshTokenService.saveWithAtId(accessToken.getJti());

        return TokenDto.builder()
            .atId(accessToken.getJti())
            .accessToken(accessToken.getToken())
            .refreshToken(refreshToken.getToken())
            .grantType("Bearer")
            .refreshExpiresIn(jwtTokenProvider.getRefreshTokenExpiration())
            .expiresIn(jwtTokenProvider.getAccessTokenExpiration())
            .build();
    }

    @Transactional
    public TokenDto processOAuthSignin(OAuth2UserInfo userInfo, String roles) {
        String email = userInfo.getEmail();

        String dummyPassword = UUID.randomUUID().toString();

        User user = usersRepository.findByEmail(email)
            .orElseGet(() -> {
                User newUser = User.builder()
                    .email(email)
                    .nickname(userInfo.getName())
                    .oauthProvider(userInfo.getProvider())
                    .oauthId(userInfo.getProviderId())
                    .password(dummyPassword)
                    .role(Role.ROLE_USER)
                    .isDeleted(false)
                    .build();
                return usersRepository.save(newUser);
            });

        return processTokenSignin(user.getEmail(), user.getRole().name());
    }

    private boolean isGoogleUser(String email) {
        User user = usersRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("해당 이메일의 유저가 없습니다."));

        return "Google".equals(user.getOauthProvider());
    }

}