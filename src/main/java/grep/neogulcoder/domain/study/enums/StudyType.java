package grep.neogulcoder.domain.study.enums;

public enum StudyType {
    ONLINE("온라인"), OFFLINE("오프라인"), HYBRID("병행");

    private final String description;

    StudyType(String description) {
        this.description = description;
    }

    private boolean equalsDescription(String description) {
        return this.description.equals(description);
    }
}
