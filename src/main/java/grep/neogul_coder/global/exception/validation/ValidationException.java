package grep.neogul_coder.global.exception.validation;

import grep.neogul_coder.global.response.ErrorCode;

public class ValidationException extends RuntimeException{
    private ErrorCode errorCode;

    public ValidationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
