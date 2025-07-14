package grep.neogul_coder.domain.review;

public enum GoodReviewTag implements ReviewTag {
    RELIABLE_EXECUTION("주어진 역할은 무리 없이 잘 해냈어요."),
    SMOOTH_COLLABORATION("협업 중 큰 문제가 없어서 편하게 함께할 수 있었어요."),
    PUNCTUAL_AND_COMMUNICATIVE("일정에 맞춰 참여했고, 필요한 만큼 소통했어요."),
    GOOD_ADAPTATION("스터디 분위기를 잘 따라왔고, 팀에 잘 녹아들었어요.");

    private final String description;

    GoodReviewTag(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public ReviewType getReviewType() {
        return ReviewType.GOOD;
    }

}
