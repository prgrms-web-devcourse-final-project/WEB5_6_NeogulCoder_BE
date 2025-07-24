package grep.neogulcoder.domain.review;

public enum ExcellentReviewTag implements ReviewTag {
    INITIATIVE("항상 먼저 도와주고 분위기를 이끌어주는 팀원이었어요."),
    RESPONSIBLE("책임감이 넘치고 맡은 일 이상으로 기여해줘서 감동이었어요."),
    EFFICIENT("꼼꼼하고 빠른 진행 덕분에 팀 전체가 수월하게 움직였어요."),
    COMMUNICATOR("커뮤니케이션도 최고, 실력도 최고! 이런 팀원은 흔치 않아요.");

    private final String description;

    ExcellentReviewTag(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public ReviewType getReviewType() {
        return ReviewType.EXCELLENT;
    }

}
