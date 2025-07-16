package grep.neogul_coder.global.exception.advice;

import grep.neogul_coder.global.exception.CommonException;
import grep.neogul_coder.global.exception.business.BusinessException;
import grep.neogul_coder.global.response.ApiResponse;
import grep.neogul_coder.global.response.code.CommonCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "grep.neogul_coder")
public class WebExceptionAdvice {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ApiResponse<String>> commonExHandler(CommonException ex) {
        return ResponseEntity
            .status(ex.code().getStatus())
            .body(ApiResponse.errorWithoutData(ex.code()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<String>> businessExHandler(BusinessException ex) {
        return ResponseEntity
            .status(ex.errorCode().getStatus())
            .body(ApiResponse.errorWithoutData(ex.errorCode()));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse<String>> authorizationDeniedExHandler(AuthorizationDeniedException ex) {
        return ResponseEntity
            .status(CommonCode.UNAUTHORIZED.getStatus())
            .body(ApiResponse.errorWithoutData(CommonCode.UNAUTHORIZED));
    }
}
