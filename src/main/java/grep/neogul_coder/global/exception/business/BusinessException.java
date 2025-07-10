package grep.neogul_coder.global.exception.business;

import grep.neogul_coder.global.response.ErrorCode;

public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;

    public BusinessException(String message) {
        super(message);
    }
}
