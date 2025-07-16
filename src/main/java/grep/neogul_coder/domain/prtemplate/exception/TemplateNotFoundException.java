package grep.neogul_coder.domain.prtemplate.exception;

import grep.neogul_coder.global.exception.business.NotFoundException;
import grep.neogul_coder.global.response.code.ErrorCode;

public class TemplateNotFoundException extends NotFoundException {

    public TemplateNotFoundException(ErrorCode errorcode) {
        super(errorcode);
    }
}
