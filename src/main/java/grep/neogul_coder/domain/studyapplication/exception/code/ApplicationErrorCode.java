package grep.neogul_coder.domain.studyapplication.exception.code;

import grep.neogul_coder.global.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApplicationErrorCode implements ErrorCode {
    APPLICATION_NOT_FOUND("SA001",HttpStatus.NOT_FOUND,"신청서를 찾을 수 없습니다."),

    ALREADY_APPLICATION("SA002", HttpStatus.BAD_REQUEST, "이미 지원한 모집글입니다."),
    APPLICATION_NOT_APPLYING("SA003", HttpStatus.BAD_REQUEST, "신청 상태가 APPLYING이 아닙니다."),

    LEADER_CANNOT_APPLY("SA004", HttpStatus.BAD_REQUEST, "스터디장은 스터디를 신청할 수 없습니다."),
    LEADER_ONLY_APPROVED("SA005", HttpStatus.BAD_REQUEST, "스터디장만 승인이 가능합니다."),
    LEADER_ONLY_REJECTED("SA006", HttpStatus.BAD_REQUEST, "스터디장만 거절이 가능합니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

    ApplicationErrorCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
