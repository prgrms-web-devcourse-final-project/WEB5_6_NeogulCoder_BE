package grep.neogul_coder.domain.users.exception;

import grep.neogul_coder.global.exception.business.NotFoundException;
import grep.neogul_coder.global.response.code.ErrorCode;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(ErrorCode errorcode) {
        super(errorcode);
    }

}
