package grep.neogul_coder.domain.users.exception;

import grep.neogul_coder.global.exception.CommonException;
import grep.neogul_coder.global.response.CommonCode;

public class PasswordNotMatchException extends CommonException {

    public PasswordNotMatchException(CommonCode code, Exception e) {
        super(code, e);
    }

    public PasswordNotMatchException(CommonCode code) {
        super(code);
    }
}
