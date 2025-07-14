package grep.neogul_coder.global.exception.validation;

import grep.neogul_coder.global.response.code.ErrorCode;

public class DuplicatedException extends ValidationException {
    public DuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
