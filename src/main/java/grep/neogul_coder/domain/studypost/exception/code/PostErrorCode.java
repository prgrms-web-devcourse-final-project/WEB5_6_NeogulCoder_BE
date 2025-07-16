package grep.neogul_coder.domain.studypost.exception.code;

import grep.neogul_coder.global.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PostErrorCode implements ErrorCode {

    POST_NOT_FOUND("P001",HttpStatus.BAD_REQUEST,"게시글을 찾을 수 없습니다.");



    private final String code;
    private final HttpStatus status;
    private final String message;

    PostErrorCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }




}
