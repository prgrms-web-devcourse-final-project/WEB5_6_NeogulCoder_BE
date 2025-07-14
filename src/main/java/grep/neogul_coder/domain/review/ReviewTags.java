package grep.neogul_coder.domain.review;

import grep.neogul_coder.global.exception.business.BusinessException;

import java.util.ArrayList;
import java.util.List;

import static grep.neogul_coder.domain.review.ReviewErrorCode.NOT_SINGLE_REVIEW_TYPE;

public class ReviewTags {

    private List<ReviewTag> reviewTags;

    private ReviewTags(List<ReviewTag> reviewTags) {
        this.reviewTags = reviewTags;
    }

    public static ReviewTags from(List<ReviewTag> reviewTags) {
        return new ReviewTags(reviewTags);
    }

    public ReviewType ensureSingleReviewType() {
        List<ReviewTag> reviewTagList = new ArrayList<>(reviewTags);
        ReviewType firstReviewType = reviewTagList.getFirst().getReviewType();

        boolean hasNotSingleType = reviewTagList.stream()
                .anyMatch(tag -> firstReviewType.isNotSameType(tag.getReviewType()));

        if (hasNotSingleType) {
            throw new BusinessException(NOT_SINGLE_REVIEW_TYPE, NOT_SINGLE_REVIEW_TYPE.getMessage());
        }
        return firstReviewType;
    }

    public List<ReviewTag> getReviewTags() {
        return new ArrayList<>(reviewTags);
    }
}
