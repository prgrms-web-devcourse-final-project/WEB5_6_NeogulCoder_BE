package grep.neogulcoder.global.exception;

import grep.neogulcoder.global.response.code.Code;

public class AuthApiException extends CommonException {

    public AuthApiException(Code code) {
        super(code);
    }

    public AuthApiException(Code code, Exception e) {
        super(code, e);
    }

}
