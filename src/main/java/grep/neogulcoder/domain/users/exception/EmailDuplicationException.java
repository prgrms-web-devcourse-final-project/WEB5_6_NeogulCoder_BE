package grep.neogulcoder.domain.users.exception;

import grep.neogulcoder.global.exception.validation.DuplicatedException;
import grep.neogulcoder.global.response.code.ErrorCode;

public class EmailDuplicationException extends DuplicatedException {

    public EmailDuplicationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
