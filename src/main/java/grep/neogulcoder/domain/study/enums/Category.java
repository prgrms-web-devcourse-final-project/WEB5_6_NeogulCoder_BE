package grep.neogulcoder.domain.study.enums;

import grep.neogulcoder.global.exception.business.NotFoundException;

import java.util.Arrays;

import static grep.neogulcoder.domain.recruitment.RecruitmentErrorCode.BAD_REQUEST_STUDY_CATEGORY;

public enum Category {
    LANGUAGE("어학"),
    IT("IT"),
    EXAM("고시/자격증"),
    FINANCE("금융"),
    MANAGEMENT("경영"),
    DESIGN("디자인"),
    ART("예술"),
    PHOTO_VIDEO("사진/영상"),
    BEAUTY("뷰티"),
    SPORTS("스포츠"),
    HOBBY("취미"),
    ETC("기타");

    private final String description;

    Category(String description) {
        this.description = description;
    }

    public static Category fromDescription(String description) {
        return Arrays.stream(values())
                .filter(category -> category.equalsDescription(description))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(BAD_REQUEST_STUDY_CATEGORY));
    }

    private boolean equalsDescription(String description) {
        return this.description.equals(description);
    }

    public String getDescription() {
        return description;
    }
}
