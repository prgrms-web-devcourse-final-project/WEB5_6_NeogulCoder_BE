package grep.neogul_coder.domain.recruitment;

import grep.neogul_coder.global.exception.business.NotFoundException;

import java.util.Arrays;

import static grep.neogul_coder.domain.recruitment.RecruitmentErrorCode.*;

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
