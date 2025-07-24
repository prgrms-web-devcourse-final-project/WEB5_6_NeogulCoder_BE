package grep.neogulcoder.domain.buddy.exception;

import grep.neogulcoder.global.exception.business.NotFoundException;
import grep.neogulcoder.domain.buddy.exception.code.BuddyEnergyErrorCode;

public class BuddyEnergyNotFoundException extends NotFoundException {
    public BuddyEnergyNotFoundException(BuddyEnergyErrorCode errorCode) {
        super(errorCode);
    }
}
