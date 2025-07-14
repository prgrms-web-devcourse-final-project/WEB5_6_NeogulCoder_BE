package grep.neogul_coder.global.exception.business;

import grep.neogul_coder.global.response.ErrorCode;

public class NotFoundException extends BusinessException{
    public NotFoundException(ErrorCode errorcode, String message) {
        super(errorcode, message);
    }
}
