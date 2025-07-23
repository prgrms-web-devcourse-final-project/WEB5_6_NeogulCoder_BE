package grep.neogulcoder.domain.users.exception;

import grep.neogulcoder.global.exception.business.BusinessException;
import grep.neogulcoder.global.response.code.ErrorCode;

public class UnActivatedUserException extends BusinessException {

    public UnActivatedUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
