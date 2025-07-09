package grep.neogul_coder.global.exception;

import grep.neogul_coder.global.response.ResponseCode;

public class AuthWebException extends CommonException {

    public AuthWebException(ResponseCode code) {
        super(code);
    }

    public AuthWebException(ResponseCode code, Exception e) {
        super(code, e);
    }
}
