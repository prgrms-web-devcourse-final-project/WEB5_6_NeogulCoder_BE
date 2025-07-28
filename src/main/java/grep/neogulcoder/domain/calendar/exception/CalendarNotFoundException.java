package grep.neogulcoder.domain.calendar.exception;

import grep.neogulcoder.global.exception.business.NotFoundException;
import grep.neogulcoder.global.response.code.ErrorCode;

public class CalendarNotFoundException extends NotFoundException {

    public CalendarNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
