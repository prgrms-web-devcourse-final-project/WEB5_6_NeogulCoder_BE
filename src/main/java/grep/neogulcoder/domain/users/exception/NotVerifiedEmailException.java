package grep.neogulcoder.domain.users.exception;

import grep.neogulcoder.global.exception.business.BusinessException;
import grep.neogulcoder.global.response.code.ErrorCode;

public class NotVerifiedEmailException extends BusinessException {

    public NotVerifiedEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
