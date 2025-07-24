package grep.neogulcoder.domain.users.exception;

import grep.neogulcoder.global.response.code.ErrorCode;
import grep.neogulcoder.global.exception.validation.ValidationException;

public class PasswordUncheckException extends ValidationException {

    public PasswordUncheckException(ErrorCode errorCode) {
        super(errorCode);
    }
}
