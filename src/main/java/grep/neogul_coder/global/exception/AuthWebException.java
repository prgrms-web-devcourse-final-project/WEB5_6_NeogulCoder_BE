package grep.neogul_coder.global.exception;

import grep.neogul_coder.global.code.Code;

public class AuthWebException extends CommonException {

    public AuthWebException(Code code) {
        super(code);
    }

    public AuthWebException(Code code, Exception e) {
        super(code, e);
    }
}
