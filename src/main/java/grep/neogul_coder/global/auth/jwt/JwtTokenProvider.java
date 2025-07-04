package grep.neogul_coder.global.auth.jwt;

import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.auth.jwt.dto.AccessTokenDto;
import grep.neogul_coder.global.config.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsServiceImpl userDetailsService;

    @Value("${jwt.secret}")
    private String key;

    @Getter
    @Value("{jwt.expiration}")
    private long accessTokenExpiration;

    @Getter
    @Value("{jwt.refresh-expiration}")
    private long refreshTokenExpiration;

    private SecretKey secretKey;

    public SecretKey getSecretKey() {
        if (secretKey == null) {
            String keyBase64Encoded = Base64.getEncoder().encodeToString(key.getBytes());
            this.secretKey = Keys.hmacShaKeyFor(keyBase64Encoded.getBytes(StandardCharsets.UTF_8));
        }
        return secretKey;
    }

    public AccessTokenDto generateAccessToken(String username, String roles) {
        String id = UUID.randomUUID().toString();
        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + accessTokenExpiration);
        String token = Jwts.builder()
            .subject(username)
            .id(id)
            .claim("roles", roles)
            .expiration(accessTokenExpiresIn)
            .signWith(getSecretKey())
            .compact();

        return AccessTokenDto.builder()
            .jti(id)
            .token(token)
            .expires(accessTokenExpiresIn.getTime())
            .build();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        Collection<? extends GrantedAuthority> authorities =
            userDetailsService.findUserAuthorities(claims.getSubject());

        Principal principal = new Principal(claims.getSubject(), "", authorities);
        principal.setAccessToken(accessToken);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser().verifyWith(getSecretKey()).build()
                .parseSignedClaims(accessToken)
                .getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
