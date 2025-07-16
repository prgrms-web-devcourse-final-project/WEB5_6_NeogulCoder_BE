package grep.neogul_coder.domain.calender.exception;

import grep.neogul_coder.global.exception.business.NotFoundException;
import grep.neogul_coder.global.response.code.ErrorCode;

public class CalendarNotFoundException extends NotFoundException {

    public CalendarNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
