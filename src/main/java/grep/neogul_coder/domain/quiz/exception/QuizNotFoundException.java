package grep.neogul_coder.domain.quiz.exception;

import grep.neogul_coder.global.exception.business.NotFoundException;
import grep.neogul_coder.global.response.code.ErrorCode;

public class QuizNotFoundException extends NotFoundException {

    public QuizNotFoundException(ErrorCode errorcode) {
        super(errorcode);
    }
}
