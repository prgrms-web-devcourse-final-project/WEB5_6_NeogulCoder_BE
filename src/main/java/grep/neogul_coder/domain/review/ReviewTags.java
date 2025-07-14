package grep.neogul_coder.domain.review;

import grep.neogul_coder.global.exception.business.BusinessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public Map<ReviewType, Map<ReviewTag, Integer>> countTagsGroupedByReviewType() {
        List<ReviewTag> reviewTagList = new ArrayList<>(reviewTags);
        return reviewTagList.stream()
                .collect(Collectors.groupingBy(
                        ReviewTag::getReviewType,
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.collectingAndThen(
                                        Collectors.counting(),
                                        Long::intValue
                                )
                        )
                ));
    }

    public List<ReviewTag> getReviewTags() {
        return new ArrayList<>(reviewTags);
    }
}
