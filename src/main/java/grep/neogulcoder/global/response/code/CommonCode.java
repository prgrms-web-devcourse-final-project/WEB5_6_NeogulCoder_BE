package grep.neogulcoder.global.response.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonCode implements Code {
    OK("2000", HttpStatus.OK, "정상적으로 처리"),
    CREATED("create", HttpStatus.CREATED, "성공적으로 생성 하였습니다."),
    NO_CONTENT("no_content", HttpStatus.NO_CONTENT, "성공적으로 처리되었으나 반환할 컨텐츠가 없습니다."),
    BAD_REQUEST("3001", HttpStatus.BAD_REQUEST, "잘못된 요청"),
    UNAUTHORIZED("3002", HttpStatus.UNAUTHORIZED, "권한 정보가 없습니다."),
    NOT_FOUND("3003", HttpStatus.NOT_FOUND, "정보를 찾을 수 없습니다."),
    BAD_CREDENTIAL("4004", HttpStatus.OK, "아이디나 비밀번호가 틀렸습니다."),
    NOT_EXIST_PRE_AUTH_CREDENTIAL("4005", HttpStatus.OK, "사전 인증 정보가 요청에서 발견되지 않았습니다."),
    INTERNAL_SERVER_ERROR("5000", HttpStatus.INTERNAL_SERVER_ERROR, "서버에러 입니다."),
    SECURITY_INCIDENT("6000", HttpStatus.OK, "비정상적인 로그인 시도가 감지되었습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

    CommonCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}