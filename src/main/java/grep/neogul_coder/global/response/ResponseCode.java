package grep.neogul_coder.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    OK("2000", HttpStatus.OK,"정상적으로 처리"),
    BAD_REQUEST("3001", HttpStatus.BAD_REQUEST, "잘못된 요청"),
    UNAUTHORIZED("3002", HttpStatus.UNAUTHORIZED, "권한 정보가 없습니다."),
    NOT_FOUND("3003", HttpStatus.NOT_FOUND,"정보를 찾을 수 없습니다.");


    private final String code;
    private final HttpStatus status;
    private final String message;


}
