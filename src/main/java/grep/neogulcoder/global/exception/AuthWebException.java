package grep.neogulcoder.global.exception;

import grep.neogulcoder.global.response.code.Code;

public class AuthWebException extends CommonException {

    public AuthWebException(Code code) {
        super(code);
    }

    public AuthWebException(Code code, Exception e) {
        super(code, e);
    }
}
