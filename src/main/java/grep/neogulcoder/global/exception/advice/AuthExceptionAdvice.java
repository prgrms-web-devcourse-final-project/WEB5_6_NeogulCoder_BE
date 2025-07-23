package grep.neogulcoder.global.exception.advice;

import grep.neogulcoder.global.exception.AuthApiException;
import grep.neogulcoder.global.exception.GoogleUserLoginException;
import grep.neogulcoder.global.response.ApiResponse;
import grep.neogulcoder.global.response.code.CommonCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class AuthExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(AuthApiException.class)
    public ResponseEntity<ApiResponse<String>> authApiExHandler(
        AuthApiException ex) {
        return ResponseEntity
            .status(ex.code().getStatus())
            .body(ApiResponse.errorWithoutData(ex.code()));
    }

    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<String>> authExHandler(
        AuthenticationException ex) {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ApiResponse.errorWithoutData(CommonCode.UNAUTHORIZED));
    }

    @ResponseBody
    @ExceptionHandler(GoogleUserLoginException.class)
    public ResponseEntity<ApiResponse<String>> googleUserLoginExHandler(GoogleUserLoginException ex) {
        return ResponseEntity
            .status(ex.code().getStatus())
            .body(ApiResponse.errorWithoutData(CommonCode.SECURITY_INCIDENT));
    }

}
