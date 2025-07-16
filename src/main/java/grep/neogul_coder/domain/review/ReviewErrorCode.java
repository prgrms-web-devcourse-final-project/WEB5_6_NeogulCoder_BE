package grep.neogul_coder.domain.review;

import grep.neogul_coder.global.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ReviewErrorCode implements ErrorCode {
    NOT_SINGLE_REVIEW_TYPE(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.name(), "단일 리뷰 타입이 아닙니다. ( ex) GOOD, BAD 혼합 )"),
    ALREADY_REVIEW_WRITE_USER(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.name(), "이미 리뷰를 작성한 회원 입니다"),

    STUDY_MEMBER_EMPTY(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.name(), "스터디 맴버들을 조회 하였으나 스터디 맴버가 없습니다"),
    STUDY_NOT_FOUND(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.name(), "스터디를 찾지 못했습니다.");

    private static final String BASIC_ERROR_NAME = "REVIEW";
    private final HttpStatus status;
    private final String code;
    private final String message;

    ReviewErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = BASIC_ERROR_NAME + ": " + code;
        this.message = message;
    }
}
