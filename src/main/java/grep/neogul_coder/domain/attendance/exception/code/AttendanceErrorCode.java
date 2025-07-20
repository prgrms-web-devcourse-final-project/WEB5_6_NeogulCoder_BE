package grep.neogul_coder.domain.attendance.exception.code;

import grep.neogul_coder.global.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AttendanceErrorCode implements ErrorCode {

    ATTENDANCE_ALREADY_CHECKED("A001", HttpStatus.BAD_REQUEST, "출석은 하루에 한 번만 가능합니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

    AttendanceErrorCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
