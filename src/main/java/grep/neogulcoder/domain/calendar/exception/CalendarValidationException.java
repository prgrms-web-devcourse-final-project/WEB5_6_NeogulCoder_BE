package grep.neogulcoder.domain.calendar.exception;

import grep.neogulcoder.domain.calendar.exception.code.CalendarErrorCode;
import grep.neogulcoder.global.exception.validation.ValidationException;

public class CalendarValidationException extends ValidationException {

    public CalendarValidationException(CalendarErrorCode errorCode) {
        super(errorCode);
    }
}
