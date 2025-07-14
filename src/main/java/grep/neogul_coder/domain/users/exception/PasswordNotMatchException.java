package grep.neogul_coder.domain.users.exception;

import grep.neogul_coder.global.exception.CommonException;
import grep.neogul_coder.global.response.Code;
import grep.neogul_coder.global.response.CommonCode;

public class PasswordNotMatchException extends CommonException {

    public PasswordNotMatchException(Code code, Exception e) {
        super(code, e);
    }

    public PasswordNotMatchException(Code code) {
        super(code);
    }
}
