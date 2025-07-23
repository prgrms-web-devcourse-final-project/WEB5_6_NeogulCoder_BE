package grep.neogulcoder.global.exception.validation;

import grep.neogulcoder.global.response.code.ErrorCode;

public class DuplicatedException extends ValidationException {
    public DuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
