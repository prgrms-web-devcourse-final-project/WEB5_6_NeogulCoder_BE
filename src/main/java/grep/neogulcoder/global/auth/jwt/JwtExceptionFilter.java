package grep.neogulcoder.global.auth.jwt;

import grep.neogulcoder.global.exception.AuthApiException;
import grep.neogulcoder.global.exception.AuthWebException;
import grep.neogulcoder.global.exception.CommonException;
import grep.neogulcoder.global.response.code.Code;
import grep.neogulcoder.global.response.code.CommonCode;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;

    public JwtExceptionFilter(
        @Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (CommonException ex) {
            throwAuthEx(request, response, ex.code());
        } catch (JwtException ex) {
            throwAuthEx(request, response, CommonCode.UNAUTHORIZED);
        }
    }

    private void throwAuthEx(HttpServletRequest request, HttpServletResponse response,
        Code code) {
        if (request.getRequestURI().startsWith("/api")) {
            handlerExceptionResolver.resolveException(request, response, null,
                new AuthApiException(code));
            return;
        }
        handlerExceptionResolver.resolveException(request, response, null,
            new AuthWebException(code));
    }
}
