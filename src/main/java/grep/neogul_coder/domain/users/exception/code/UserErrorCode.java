package grep.neogul_coder.domain.users.exception.code;

import grep.neogul_coder.global.response.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserErrorCode implements ErrorCode {

    PASSWORD_MISMATCH("U002", HttpStatus.BAD_REQUEST, "비밀번호를 다시 확인해주세요.");


    private final String code;
    private final HttpStatus status;
    private final String message;

    UserErrorCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
