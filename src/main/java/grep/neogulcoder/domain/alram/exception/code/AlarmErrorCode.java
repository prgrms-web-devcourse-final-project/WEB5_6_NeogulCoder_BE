package grep.neogulcoder.domain.alram.exception.code;

import grep.neogulcoder.global.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AlarmErrorCode implements ErrorCode {

    ALARM_NOT_FOUND("A001",HttpStatus.NOT_FOUND,"알람을 찾을 수 없습니다.");


    private final String code;
    private final HttpStatus status;
    private final String message;

    AlarmErrorCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
