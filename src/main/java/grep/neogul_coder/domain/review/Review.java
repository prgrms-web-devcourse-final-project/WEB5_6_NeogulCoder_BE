package grep.neogul_coder.domain.review;

import lombok.Builder;

import java.util.List;

public class Review {

    private ReviewType reviewType;
    private List<ReviewTag> reviewTags;
    private String content;

    @Builder
    private Review(ReviewType reviewType, List<ReviewTag> reviewTags, String content) {
        this.reviewType = reviewType;
        this.reviewTags = reviewTags;
        this.content = content;
    }
}
