package grep.neogul_coder.domain.study.exception;

import grep.neogul_coder.global.exception.business.BusinessException;
import grep.neogul_coder.global.response.code.ErrorCode;

public class StudyAlreadyStartedException extends BusinessException {

    public StudyAlreadyStartedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
