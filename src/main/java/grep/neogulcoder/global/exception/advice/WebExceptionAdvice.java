package grep.neogulcoder.global.exception.advice;

import grep.neogulcoder.global.exception.CommonException;
import grep.neogulcoder.global.exception.business.BusinessException;
import grep.neogulcoder.global.response.ApiResponse;
import grep.neogulcoder.global.response.code.CommonCode;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "grep.neogulcoder")
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleValidation(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(", "));
        return ResponseEntity
            .status(CommonCode.BAD_REQUEST.getStatus())
            .body(ApiResponse.error(errorMessage));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<String>> handleBindException(BindException ex) {
        String errorMessage = ex.getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(", "));
        return ResponseEntity
            .status(CommonCode.BAD_REQUEST.getStatus())
            .body(ApiResponse.error(errorMessage));
    }
}
