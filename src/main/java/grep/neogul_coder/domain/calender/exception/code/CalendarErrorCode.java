package grep.neogul_coder.domain.calender.exception.code;

import grep.neogul_coder.global.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CalendarErrorCode implements ErrorCode {

    MISSING_REQUIRED_FIELDS("C01", HttpStatus.BAD_REQUEST, "일정 제목과 시작/종료 시간은 필수입니다."),
    NOT_CALENDAR_OWNER("CALENDAR_003", HttpStatus.BAD_REQUEST,"작성자만 수정 또는 삭제할 수 있습니다."),
    CALENDAR_NOT_FOUND("C404", HttpStatus.NOT_FOUND, "해당 캘린더를 찾을 수 없습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

    CalendarErrorCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
