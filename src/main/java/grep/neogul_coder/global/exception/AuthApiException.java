package grep.neogul_coder.global.exception;

import grep.neogul_coder.global.code.Code;

public class AuthApiException extends CommonException {

    public AuthApiException(Code code) {
        super(code);
    }

    public AuthApiException(Code code, Exception e) {
        super(code, e);
    }

}
