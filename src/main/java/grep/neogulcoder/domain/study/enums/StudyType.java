package grep.neogulcoder.domain.study.enums;

import grep.neogulcoder.domain.recruitment.RecruitmentErrorCode;
import grep.neogulcoder.global.exception.business.NotFoundException;

import java.util.Arrays;

public enum StudyType {
    ONLINE("온라인"), OFFLINE("오프라인"), HYBRID("병행");

    private final String description;

    StudyType(String description) {
        this.description = description;
    }

    public static StudyType fromDescription(String description) {
        return Arrays.stream(values())
                .filter(study -> study.equalsDescription(description))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(RecruitmentErrorCode.BAD_REQUEST_STUDY_TYPE));
    }

    private boolean equalsDescription(String description) {
        return this.description.equals(description);
    }
}
