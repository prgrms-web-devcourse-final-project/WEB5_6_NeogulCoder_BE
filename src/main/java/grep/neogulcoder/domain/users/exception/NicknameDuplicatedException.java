package grep.neogulcoder.domain.users.exception;

import grep.neogulcoder.global.exception.validation.DuplicatedException;
import grep.neogulcoder.global.response.code.ErrorCode;

public class NicknameDuplicatedException extends DuplicatedException {

    public NicknameDuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
