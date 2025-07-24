package grep.neogulcoder.domain.studypost.exception;

import grep.neogulcoder.global.exception.business.NotFoundException;
import grep.neogulcoder.global.response.code.ErrorCode;

public class PostNotFoundException extends NotFoundException {

    public PostNotFoundException(ErrorCode errorcode) {
        super(errorcode);
    }
}
