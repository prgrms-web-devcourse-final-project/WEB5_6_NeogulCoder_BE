package grep.neogulcoder.domain.users.exception.advice;

import grep.neogulcoder.domain.users.exception.EmailDuplicationException;
import grep.neogulcoder.domain.users.exception.NicknameDuplicatedException;
import grep.neogulcoder.domain.users.exception.NotVerifiedEmailException;
import grep.neogulcoder.domain.users.exception.PasswordNotMatchException;
import grep.neogulcoder.domain.users.exception.PasswordUncheckException;
import grep.neogulcoder.domain.users.exception.UserNotFoundException;
import grep.neogulcoder.domain.users.exception.code.UserErrorCode;
import grep.neogulcoder.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> userNotFoundException(UserNotFoundException e) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.errorWithoutData(UserErrorCode.USER_NOT_FOUND));
    }

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

    @ExceptionHandler(EmailDuplicationException.class)
    public ResponseEntity<ApiResponse<Void>> mailDuplicationException(EmailDuplicationException ex) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.errorWithoutData(UserErrorCode.IS_DUPLICATED_MALI));
    }

    @ExceptionHandler(NicknameDuplicatedException.class)
    public ResponseEntity<ApiResponse<Void>> nicknameDuplicationException(NicknameDuplicatedException ex) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.errorWithoutData(UserErrorCode.IS_DUPLICATED_NICKNAME));
    }

    @ ExceptionHandler(NotVerifiedEmailException.class)
    public ResponseEntity<ApiResponse<Void>> notVerifiedEmailException(NotVerifiedEmailException ex) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.errorWithoutData(UserErrorCode.NOT_VERIFIED_EMAIL));
    }

}