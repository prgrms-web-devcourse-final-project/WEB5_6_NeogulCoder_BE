package grep.neogul_coder.domain.calender.exception.code;

import grep.neogul_coder.global.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CalendarErrorCode implements ErrorCode {

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
