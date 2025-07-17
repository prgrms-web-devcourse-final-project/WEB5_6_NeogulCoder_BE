package grep.neogul_coder.domain.calender.exception;

import grep.neogul_coder.domain.calender.exception.code.CalendarErrorCode;
import grep.neogul_coder.global.exception.validation.ValidationException;

public class CalendarValidationException extends ValidationException {

    public CalendarValidationException(CalendarErrorCode errorCode) {
        super(errorCode);
    }
}
