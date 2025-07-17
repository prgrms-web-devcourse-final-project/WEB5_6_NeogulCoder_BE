package grep.neogul_coder.domain.quiz.exception;

import grep.neogul_coder.global.exception.validation.ValidationException;
import grep.neogul_coder.global.response.code.ErrorCode;

public class PostNotFreeException extends ValidationException {

    public PostNotFreeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
