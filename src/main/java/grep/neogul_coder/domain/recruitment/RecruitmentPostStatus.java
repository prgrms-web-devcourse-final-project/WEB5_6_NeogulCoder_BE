package grep.neogul_coder.domain.recruitment;

public enum RecruitmentPostStatus {
    IN_PROGRESS("진행중"),
    COMPLETE("완료");

    private final String description;

    RecruitmentPostStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
