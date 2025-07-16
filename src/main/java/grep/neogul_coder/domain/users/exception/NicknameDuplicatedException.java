package grep.neogul_coder.domain.users.exception;

import grep.neogul_coder.global.exception.validation.DuplicatedException;
import grep.neogul_coder.global.response.code.ErrorCode;

public class NicknameDuplicatedException extends DuplicatedException {

    public NicknameDuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
