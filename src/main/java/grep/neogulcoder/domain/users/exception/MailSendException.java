package grep.neogulcoder.domain.users.exception;

import grep.neogulcoder.global.exception.business.BusinessException;
import grep.neogulcoder.global.response.code.ErrorCode;

public class MailSendException extends BusinessException {
    public MailSendException(ErrorCode errorCode) {
        super(errorCode);
    }
}
