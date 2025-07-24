package grep.neogulcoder.domain.prtemplate.exception;

import grep.neogulcoder.global.exception.business.NotFoundException;
import grep.neogulcoder.global.response.code.ErrorCode;

public class TemplateNotFoundException extends NotFoundException {

    public TemplateNotFoundException(ErrorCode errorcode) {
        super(errorcode);
    }
}
