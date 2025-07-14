package grep.neogul_coder.global.auth.jwt;

import org.springframework.http.ResponseCookie;

public class TokenCookieFactory {

    public static ResponseCookie create(String name, String value, Long expires) {
        return ResponseCookie.from(name, value)
            .maxAge(expires)
            .path("/")
            .httpOnly(true)
            .secure(true)
            .sameSite("None")
            .build();
    }

    public static ResponseCookie createExpiredToken(String name) {
        return ResponseCookie.from(name,"")
            .maxAge(0)
            .path("/")
            .httpOnly(true)
            .secure(true)
            .sameSite("None")
            .build();
    }

}