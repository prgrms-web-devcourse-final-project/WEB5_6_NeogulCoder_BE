package grep.neogul_coder.domain.study.exception.code;

import grep.neogul_coder.global.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum StudyErrorCode implements ErrorCode {

    STUDY_NOT_FOUND("S001",HttpStatus.BAD_REQUEST,"스터디를 찾을 수 없습니다."),
    STUDY_NOT_LEADER("S002", HttpStatus.BAD_REQUEST, "스터디장만 접근이 가능합니다."),
    STUDY_ALREADY_STARTED("S003", HttpStatus.BAD_REQUEST, "이미 시작된 스터디의 시작일은 변경할 수 없습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

    StudyErrorCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
