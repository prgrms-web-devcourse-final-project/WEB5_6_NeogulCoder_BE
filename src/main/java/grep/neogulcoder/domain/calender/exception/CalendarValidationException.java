package grep.neogulcoder.domain.calender.exception;

import grep.neogulcoder.domain.calender.exception.code.CalendarErrorCode;
import grep.neogulcoder.global.exception.validation.ValidationException;

public class CalendarValidationException extends ValidationException {

    public CalendarValidationException(CalendarErrorCode errorCode) {
        super(errorCode);
    }
}
