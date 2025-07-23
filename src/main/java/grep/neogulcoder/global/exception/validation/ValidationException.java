package grep.neogulcoder.global.exception.validation;

import grep.neogulcoder.global.response.code.ErrorCode;

public class ValidationException extends RuntimeException{
    private ErrorCode errorCode;

    public ValidationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
