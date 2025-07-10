package grep.neogul_coder.global.exception;

import grep.neogul_coder.global.response.ResponseCode;

public class GoogleUserLoginException extends CommonException {

    public GoogleUserLoginException(ResponseCode code) {
        super(code);
    }

    public GoogleUserLoginException(ResponseCode code, Exception e) {
        super(code, e);
    }
}
