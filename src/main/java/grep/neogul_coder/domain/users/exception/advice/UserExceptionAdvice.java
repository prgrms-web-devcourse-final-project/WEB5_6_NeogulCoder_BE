package grep.neogul_coder.domain.users.exception.advice;

import grep.neogul_coder.domain.users.exception.PasswordNotMatchException;
import grep.neogul_coder.domain.users.exception.PasswordUncheckException;
import grep.neogul_coder.domain.users.exception.code.UserErrorCode;
import grep.neogul_coder.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionAdvice {

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<ApiResponse<Void>> passwordNotMatchException(PasswordNotMatchException ex) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.errorWithoutData(UserErrorCode.PASSWORD_MISMATCH));
    }

    @ExceptionHandler(PasswordUncheckException.class)
    public ResponseEntity<ApiResponse<Void>> passwordUncheckException(PasswordUncheckException ex) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.errorWithoutData(UserErrorCode.PASSWORD_MISMATCH));
    }



}