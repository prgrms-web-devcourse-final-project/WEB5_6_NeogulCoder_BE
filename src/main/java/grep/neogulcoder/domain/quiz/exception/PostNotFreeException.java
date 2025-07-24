package grep.neogulcoder.domain.quiz.exception;

import grep.neogulcoder.global.exception.validation.ValidationException;
import grep.neogulcoder.global.response.code.ErrorCode;

public class PostNotFreeException extends ValidationException {

    public PostNotFreeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
