package grep.neogulcoder.global.auth.service;

import grep.neogulcoder.domain.buddy.service.BuddyEnergyService;
import grep.neogulcoder.domain.prtemplate.entity.Link;
import grep.neogulcoder.domain.prtemplate.entity.PrTemplate;
import grep.neogulcoder.domain.prtemplate.repository.LinkRepository;
import grep.neogulcoder.domain.prtemplate.repository.PrTemplateRepository;
import grep.neogulcoder.domain.users.entity.User;
import grep.neogulcoder.domain.users.exception.UnActivatedUserException;
import grep.neogulcoder.domain.users.exception.UserNotFoundException;
import grep.neogulcoder.domain.users.exception.code.UserErrorCode;
import grep.neogulcoder.domain.users.repository.UserRepository;
import grep.neogulcoder.global.auth.code.Role;
import grep.neogulcoder.global.auth.entity.RefreshToken;
import grep.neogulcoder.global.auth.jwt.JwtTokenProvider;
import grep.neogulcoder.global.auth.jwt.dto.AccessTokenDto;
import grep.neogulcoder.global.auth.jwt.dto.TokenDto;
import grep.neogulcoder.global.auth.oauth.user.OAuth2UserInfo;
import grep.neogulcoder.global.auth.payload.LoginRequest;
import grep.neogulcoder.global.auth.repository.UserBlackListRepository;
import grep.neogulcoder.global.exception.GoogleUserLoginException;
import grep.neogulcoder.global.response.code.CommonCode;
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
    private final LinkRepository linkRepository;
    private final PrTemplateRepository prTemplateRepository;
    private final BuddyEnergyService buddyEnergyService;

    public TokenDto signin(LoginRequest loginRequest) {

        if (isGoogleUser(loginRequest.getEmail())) {
            throw new GoogleUserLoginException(CommonCode.SECURITY_INCIDENT);
        }

        if (isUnactivatedUser(loginRequest.getEmail())) {
            throw new UnActivatedUserException(UserErrorCode.UNACTIVATED_USER);
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

        return usersRepository.findByEmail(email)
            .map(user -> processTokenSignin(user.getEmail(), user.getRole().name()))
            .orElseGet(() -> {
                User newUser = User.builder()
                    .email(email)
                    .nickname(userInfo.getName())
                    .oauthProvider(userInfo.getProvider())
                    .oauthId(userInfo.getProviderId())
                    .password(dummyPassword)
                    .role(Role.ROLE_USER)
                    .build();

                User savedUser = usersRepository.save(newUser);
                prTemplateRepository.save(PrTemplate.PrTemplateInit(savedUser.getId(), null, null));
                linkRepository.save(Link.LinkInit(savedUser.getId(), null, null));
                linkRepository.save(Link.LinkInit(savedUser.getId(), null, null));
                buddyEnergyService.createDefaultEnergy(savedUser.getId());

                return processTokenSignin(savedUser.getEmail(), savedUser.getRole().name());
            });
    }

    private boolean isUnactivatedUser(String email) {
        return !usersRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND))
            .isActivated();
    }

    private boolean isGoogleUser(String email) {
        User user = usersRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("해당 이메일의 유저가 없습니다."));

        return "Google".equals(user.getOauthProvider());
    }

}