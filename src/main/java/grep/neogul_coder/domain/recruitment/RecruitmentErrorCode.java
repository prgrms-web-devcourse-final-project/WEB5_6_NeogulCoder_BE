package grep.neogul_coder.domain.recruitment;

import grep.neogul_coder.global.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum RecruitmentErrorCode implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.name(), "모집글을 찾지 못했습니다."),
    NOT_OWNER(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.name(), "모집글을 등록한 당사자가 아닙니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    RecruitmentErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
