package grep.neogul_coder.domain.review.tag;

public enum ReviewType {
    BAD("별로에요"),
    GOOD("좋아요"),
    EXCELLENT("최고에요");

    private final String description;

    ReviewType(String description) {
        this.description = description;
    }
}
