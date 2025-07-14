package grep.neogul_coder.domain.users.exception;

import grep.neogul_coder.global.code.ErrorCode;
import grep.neogul_coder.global.exception.validation.ValidationException;

public class PasswordUncheckException extends ValidationException {

    public PasswordUncheckException(ErrorCode errorCode) {
        super(errorCode);
    }
}
