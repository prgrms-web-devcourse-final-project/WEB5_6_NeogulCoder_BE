package grep.neogul_coder.domain.review;

import grep.neogul_coder.global.response.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ReviewErrorCode implements ErrorCode {
    NOT_SINGLE_REVIEW_TYPE(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.name(), "단일 리뷰 타입이 아닙니다. ( ex) GOOD, BAD 혼합 )");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ReviewErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
