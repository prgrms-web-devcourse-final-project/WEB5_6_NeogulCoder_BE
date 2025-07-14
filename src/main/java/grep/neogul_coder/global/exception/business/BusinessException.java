package grep.neogul_coder.global.exception.business;

import grep.neogul_coder.global.response.code.ErrorCode;

public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
