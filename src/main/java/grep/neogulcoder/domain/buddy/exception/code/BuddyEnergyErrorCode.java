package grep.neogulcoder.domain.buddy.exception.code;

import grep.neogulcoder.global.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BuddyEnergyErrorCode implements ErrorCode {

    BUDDY_ENERGY_NOT_FOUND("BE404", HttpStatus.NOT_FOUND, "해당 유저의 버디 에너지를 찾을 수 없습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

    BuddyEnergyErrorCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
