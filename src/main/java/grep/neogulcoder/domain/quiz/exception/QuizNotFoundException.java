package grep.neogulcoder.domain.quiz.exception;

import grep.neogulcoder.global.exception.business.NotFoundException;
import grep.neogulcoder.global.response.code.ErrorCode;

public class QuizNotFoundException extends NotFoundException {

    public QuizNotFoundException(ErrorCode errorcode) {
        super(errorcode);
    }
}
