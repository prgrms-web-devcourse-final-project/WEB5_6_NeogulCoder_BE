package grep.neogul_coder.global.exception;

import grep.neogul_coder.global.response.ResponseCode;

public class AuthApiException extends CommonException {

    public AuthApiException(ResponseCode code) {
        super(code);
    }

    public AuthApiException(ResponseCode code, Exception e) {
        super(code, e);
    }

}
