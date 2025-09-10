package grep.neogulcoder.domain.users.exception;

import grep.neogulcoder.global.exception.business.BusinessException;
import grep.neogulcoder.global.response.code.ErrorCode;

public class MailTaskRejectedException extends BusinessException {
    public MailTaskRejectedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
