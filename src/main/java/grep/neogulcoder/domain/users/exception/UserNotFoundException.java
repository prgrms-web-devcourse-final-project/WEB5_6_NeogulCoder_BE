package grep.neogulcoder.domain.users.exception;

import grep.neogulcoder.global.exception.business.NotFoundException;
import grep.neogulcoder.global.response.code.ErrorCode;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(ErrorCode errorcode) {
        super(errorcode);
    }

}
