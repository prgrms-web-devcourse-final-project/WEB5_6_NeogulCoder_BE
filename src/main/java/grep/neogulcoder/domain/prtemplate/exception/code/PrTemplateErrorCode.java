package grep.neogulcoder.domain.prtemplate.exception.code;

import grep.neogulcoder.global.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PrTemplateErrorCode implements ErrorCode {

    TEMPLATE_NOT_FOUND("T001", HttpStatus.BAD_REQUEST, "템플릿을 찾을 수 없습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

    PrTemplateErrorCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

}