package grep.neogulcoder.domain.users.exception;

import grep.neogulcoder.global.response.code.ErrorCode;
import grep.neogulcoder.global.exception.validation.ValidationException;

public class PasswordNotMatchException extends ValidationException {

    public PasswordNotMatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}
