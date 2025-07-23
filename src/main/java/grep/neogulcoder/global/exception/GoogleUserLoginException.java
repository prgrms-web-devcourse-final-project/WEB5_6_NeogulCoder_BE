package grep.neogulcoder.global.exception;

import grep.neogulcoder.global.response.code.CommonCode;

public class GoogleUserLoginException extends CommonException {

    public GoogleUserLoginException(CommonCode code) {
        super(code);
    }

    public GoogleUserLoginException(CommonCode code, Exception e) {
        super(code, e);
    }
}
