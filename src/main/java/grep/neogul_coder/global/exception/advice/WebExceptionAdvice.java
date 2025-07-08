package grep.neogul_coder.global.exception.advice;

import grep.neogul_coder.global.exception.CommonException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "grep.neogul_coder")
public class WebExceptionAdvice {

    @ExceptionHandler(CommonException.class)
    public String webExceptionHandler(CommonException ex, Model model) {
        model.addAttribute("message", ex.code().getMessage());
        return "error/redirect";
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public String authorizationDeniedHandler(AuthorizationDeniedException ex, Model model) {
        model.addAttribute("message", "접근 권한 없음");
        return "error/redirect";
    }

}
