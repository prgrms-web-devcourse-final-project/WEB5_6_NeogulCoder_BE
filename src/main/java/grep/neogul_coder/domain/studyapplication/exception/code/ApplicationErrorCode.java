package grep.neogul_coder.domain.studyapplication.exception.code;

import grep.neogul_coder.global.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApplicationErrorCode implements ErrorCode {
    ALREADY_APPLICATION("SA001", HttpStatus.BAD_REQUEST, "이미 지원한 모집글입니다."),
    LEADER_CANNOT_APPLY("SA002", HttpStatus.BAD_REQUEST, "스터디장은 스터디를 신청할 수 없습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

    ApplicationErrorCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
