package grep.neogul_coder.domain.buddy.exception;

import grep.neogul_coder.global.exception.business.NotFoundException;
import grep.neogul_coder.domain.buddy.exception.code.BuddyEnergyErrorCode;

public class BuddyEnergyNotFoundException extends NotFoundException {
    public BuddyEnergyNotFoundException(BuddyEnergyErrorCode errorCode) {
        super(errorCode);
    }
}
