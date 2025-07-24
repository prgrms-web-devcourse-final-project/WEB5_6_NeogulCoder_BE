package grep.neogulcoder.domain.recruitment;

import grep.neogulcoder.global.exception.business.NotFoundException;

import java.util.Arrays;

import static grep.neogulcoder.domain.recruitment.RecruitmentErrorCode.*;

public enum RecruitmentPostStatus {
    IN_PROGRESS("진행중"),
    COMPLETE("완료");

    private final String description;

    RecruitmentPostStatus(String description) {
        this.description = description;
    }

    public static RecruitmentPostStatus fromDescription(String description) {
        return Arrays.stream(values())
                .filter(status -> status.equalsDescription(description))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(BAD_REQUEST_STATUS));
    }

    private boolean equalsDescription(String description) {
        return this.description.equals(description);
    }

    public String getDescription() {
        return description;
    }
}
