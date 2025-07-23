package grep.neogulcoder.domain.quiz.exception.advice;

import grep.neogulcoder.domain.quiz.exception.PostNotFreeException;
import grep.neogulcoder.domain.quiz.exception.QuizNotFoundException;
import grep.neogulcoder.domain.quiz.exception.code.QuizErrorCode;
import grep.neogulcoder.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class QuizAdvice {

    @ExceptionHandler(PostNotFreeException.class)
    public ResponseEntity<ApiResponse<Void>> postNotFreeException(PostNotFreeException e) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.errorWithoutData(QuizErrorCode.POST_NOT_FREE_ERROR));
    }

    @ExceptionHandler(QuizNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> quizNotFreeException(PostNotFreeException e) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.errorWithoutData(QuizErrorCode.QUIZ_NOT_FOUND));
    }

}