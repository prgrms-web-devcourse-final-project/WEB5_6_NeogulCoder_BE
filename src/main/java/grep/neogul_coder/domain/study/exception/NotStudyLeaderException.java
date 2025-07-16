package grep.neogul_coder.domain.study.exception;

import grep.neogul_coder.global.exception.business.BusinessException;
import grep.neogul_coder.global.response.code.ErrorCode;

public class NotStudyLeaderException extends BusinessException {

    public NotStudyLeaderException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
