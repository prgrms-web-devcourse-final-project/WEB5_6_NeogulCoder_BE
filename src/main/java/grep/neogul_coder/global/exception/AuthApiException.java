package grep.neogul_coder.global.exception;

import grep.neogul_coder.global.response.CommonCode;

public class AuthApiException extends CommonException {

    public AuthApiException(CommonCode code) {
        super(code);
    }

    public AuthApiException(CommonCode code, Exception e) {
        super(code, e);
    }

}
