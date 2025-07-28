package grep.neogulcoder.domain.groupchat.exception.code;

import grep.neogulcoder.global.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum GroupChatErrorCode implements ErrorCode {

    CHAT_ROOM_NOT_FOUND("G01", HttpStatus.NOT_FOUND, "채팅방을 찾을 수 없습니다."),
    USER_NOT_FOUND("G02", HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    USER_NOT_IN_STUDY("G03", HttpStatus.FORBIDDEN, "해당 스터디에 참가한 사용자만 채팅할 수 있습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

    GroupChatErrorCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
