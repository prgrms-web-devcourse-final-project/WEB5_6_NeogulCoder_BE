package grep.neogul_coder.domain.users.exception;

import grep.neogul_coder.global.exception.business.BusinessException;
import grep.neogul_coder.global.response.code.ErrorCode;

public class NotVerifiedEmailException extends BusinessException {

    public NotVerifiedEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
