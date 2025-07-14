package grep.neogul_coder.domain.users.exception;

import grep.neogul_coder.global.exception.CommonException;
import grep.neogul_coder.global.response.Code;

public class PasswordUncheckException extends CommonException {

    public PasswordUncheckException(Code code) {
        super(code);
    }

    public PasswordUncheckException(Code code, Exception e) {
        super(code, e);
    }
}
