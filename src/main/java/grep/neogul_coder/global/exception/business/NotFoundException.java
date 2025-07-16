package grep.neogul_coder.global.exception.business;

import grep.neogul_coder.global.response.code.ErrorCode;

public class NotFoundException extends BusinessException{
    public NotFoundException(ErrorCode errorcode) {
        super(errorcode);
    }
}
