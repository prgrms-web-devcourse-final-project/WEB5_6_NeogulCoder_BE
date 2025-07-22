package grep.neogul_coder.domain.users.exception;

import grep.neogul_coder.global.exception.business.BusinessException;
import grep.neogul_coder.global.response.code.ErrorCode;

public class UnActivatedUserException extends BusinessException {

    public UnActivatedUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
