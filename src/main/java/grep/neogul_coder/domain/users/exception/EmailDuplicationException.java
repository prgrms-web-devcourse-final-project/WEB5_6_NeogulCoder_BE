package grep.neogul_coder.domain.users.exception;

import grep.neogul_coder.global.exception.validation.DuplicatedException;
import grep.neogul_coder.global.response.code.ErrorCode;

public class EmailDuplicationException extends DuplicatedException {

    public EmailDuplicationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
