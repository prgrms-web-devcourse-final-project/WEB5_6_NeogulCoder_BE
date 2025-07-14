package grep.neogul_coder.global.auth.jwt;

import grep.neogul_coder.global.exception.AuthApiException;
import grep.neogul_coder.global.code.CommonCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.password.CompromisedPasswordException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver resolver;
    public JwtAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {

        log.info("request uri: {}", request.getRequestURI());

        CommonCode commonCode = switch (authException){
            case BadCredentialsException bce ->{
                log.warn("{}", bce.getMessage());
                yield CommonCode.BAD_CREDENTIAL;
            }
            case InsufficientAuthenticationException iae -> {
                log.warn("{}", iae.getMessage());
                yield CommonCode.UNAUTHORIZED;
            }
            case CompromisedPasswordException cpe -> {
                log.warn("{}", cpe.getMessage());
                yield CommonCode.NOT_EXIST_PRE_AUTH_CREDENTIAL;
            }
            default -> {
                log.error(authException.getMessage(), authException);
                yield CommonCode.BAD_REQUEST;
            }
        };

        if(request.getRequestURI().startsWith("/api")) {
            resolver.resolveException(request, response, null,
                new AuthApiException(commonCode));
            return;
        }

        resolver.resolveException(request, response, null, new AuthApiException(commonCode));
    }
}