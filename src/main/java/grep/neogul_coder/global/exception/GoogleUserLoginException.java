package grep.neogul_coder.global.exception;

import grep.neogul_coder.global.response.CommonCode;

public class GoogleUserLoginException extends CommonException {

    public GoogleUserLoginException(CommonCode code) {
        super(code);
    }

    public GoogleUserLoginException(CommonCode code, Exception e) {
        super(code, e);
    }
}
