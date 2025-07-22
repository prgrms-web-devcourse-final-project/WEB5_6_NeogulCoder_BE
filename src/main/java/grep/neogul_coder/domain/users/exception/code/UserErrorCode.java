package grep.neogul_coder.domain.users.exception.code;

import grep.neogul_coder.global.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserErrorCode implements ErrorCode {

    USER_NOT_FOUND("U001",HttpStatus.BAD_REQUEST,"회원을 찾을 수 없습니다."),
    PASSWORD_MISMATCH("U002", HttpStatus.BAD_REQUEST, "비밀번호를 다시 확인해주세요."),
    PASSWORD_UNCHECKED("U003", HttpStatus.BAD_REQUEST, "비밀번호와 비밀번호 확인이 다릅니다"),
    IS_DUPLICATED_MALI("U004", HttpStatus.BAD_REQUEST,"이미 존재하는 이메일입니다."),
    IS_DUPLICATED_NICKNAME("U005", HttpStatus.BAD_REQUEST,"이미 존재하는 닉네임입니다."),
    UNACTIVATED_USER("U006", HttpStatus.BAD_REQUEST,"탈퇴된 회원입니다."),
    NOT_VERIFIED_EMAIL("U007", HttpStatus.BAD_REQUEST, "메일 인증이 되지 않은 이메일입니다.");


    private final String code;
    private final HttpStatus status;
    private final String message;

    UserErrorCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
