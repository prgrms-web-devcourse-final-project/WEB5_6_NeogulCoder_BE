package grep.neogulcoder.global.exception.business;

import grep.neogulcoder.global.response.code.ErrorCode;

public class NotFoundException extends BusinessException{
    public NotFoundException(ErrorCode errorcode) {
        super(errorcode);
    }
}
