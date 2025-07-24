package grep.neogulcoder.domain.review;

public enum BadReviewTag implements ReviewTag {
    LOW_COMMITMENT("약속된 일정에 자주 늦거나 참여율이 낮았어요."),
    LOW_COMMUNICATION("소통이 원활하지 않아 협업에 어려움이 있었어요."),
    LACK_OF_RESPONSIBILITY("역할에 대한 책임감이 부족해 보였어요."),
    TOO_INDIVIDUALISTIC("팀 작업보다 개인 일에 더 집중한 느낌이었어요.");

    private final String description;

    BadReviewTag(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public ReviewType getReviewType() {
        return ReviewType.BAD;
    }

}
