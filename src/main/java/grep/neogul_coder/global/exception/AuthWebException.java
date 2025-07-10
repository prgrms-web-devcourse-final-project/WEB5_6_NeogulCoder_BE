package grep.neogul_coder.global.exception;

import grep.neogul_coder.global.response.CommonCode;

public class AuthWebException extends CommonException {

    public AuthWebException(CommonCode code) {
        super(code);
    }

    public AuthWebException(CommonCode code, Exception e) {
        super(code, e);
    }
}
