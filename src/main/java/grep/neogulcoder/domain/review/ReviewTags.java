package grep.neogulcoder.domain.review;

import grep.neogulcoder.global.exception.business.BusinessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static grep.neogulcoder.domain.review.ReviewErrorCode.NOT_SINGLE_REVIEW_TYPE;

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
            throw new BusinessException(NOT_SINGLE_REVIEW_TYPE);
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

    public List<String> extractDescription(){
        List<ReviewTag> reviewTagList = new ArrayList<>(reviewTags);

        return reviewTagList.stream()
                .map(ReviewTag::getDescription)
                .toList();
    }

    public List<ReviewTag> getReviewTags() {
        return new ArrayList<>(reviewTags);
    }
}
