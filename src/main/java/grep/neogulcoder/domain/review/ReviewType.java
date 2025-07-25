package grep.neogulcoder.domain.review;

import lombok.Getter;

@Getter
public enum ReviewType {
    BAD("별로에요"),
    GOOD("좋아요"),
    EXCELLENT("최고에요");

    private final String description;

    ReviewType(String description) {
        this.description = description;
    }

    public boolean isSameType(ReviewType reviewType) {
        return this == reviewType;
    }

    public boolean isNotSameType(ReviewType reviewType) {
        return !isSameType(reviewType);
    }

    public boolean isPositive() {
        return this == GOOD || this == EXCELLENT;
    }
}
