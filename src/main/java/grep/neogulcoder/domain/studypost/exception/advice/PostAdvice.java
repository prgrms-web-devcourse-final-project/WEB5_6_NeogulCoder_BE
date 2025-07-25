package grep.neogulcoder.domain.studypost.exception.advice;

import grep.neogulcoder.domain.studypost.exception.PostNotFoundException;
import grep.neogulcoder.domain.users.exception.UserNotFoundException;
import grep.neogulcoder.domain.users.exception.code.UserErrorCode;
import grep.neogulcoder.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PostAdvice {

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> postNotFoundException(UserNotFoundException e) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.errorWithoutData(UserErrorCode.USER_NOT_FOUND));
    }

}
