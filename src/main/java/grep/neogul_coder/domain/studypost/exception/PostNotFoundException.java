package grep.neogul_coder.domain.studypost.exception;

import grep.neogul_coder.global.exception.business.NotFoundException;
import grep.neogul_coder.global.response.code.ErrorCode;

public class PostNotFoundException extends NotFoundException {

    public PostNotFoundException(ErrorCode errorcode) {
        super(errorcode);
    }
}
