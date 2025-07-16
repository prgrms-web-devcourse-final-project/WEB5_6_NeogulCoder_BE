package grep.neogul_coder.domain.quiz.exception.code;

import grep.neogul_coder.global.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum QuizErrorCode implements ErrorCode {

    POST_NOT_FREE_ERROR("Q001",HttpStatus.BAD_REQUEST,"자유 게시글이 아닙니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

    QuizErrorCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

}
