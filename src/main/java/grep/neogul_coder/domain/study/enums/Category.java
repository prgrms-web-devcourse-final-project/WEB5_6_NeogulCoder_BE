package grep.neogul_coder.domain.study.enums;

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

    public String getDescription() {
        return description;
    }
}
