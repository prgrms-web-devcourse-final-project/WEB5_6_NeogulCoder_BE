package grep.neogulcoder.domain.review.service.request;

import grep.neogulcoder.domain.review.Review;
import grep.neogulcoder.domain.review.ReviewTag;
import grep.neogulcoder.domain.review.ReviewType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReviewSaveServiceRequest {

    private long studyId;
    private long targetUserId;
    private ReviewType reviewType;
    private List<String> reviewTag;
    private String content;

    @Builder
    private ReviewSaveServiceRequest(long studyId, long targetUserId, ReviewType reviewType,
                                     List<String> reviewTag, String content) {
        this.studyId = studyId;
        this.targetUserId = targetUserId;
        this.reviewType = reviewType;
        this.reviewTag = reviewTag;
        this.content = content;
    }

    public Review toReview(List<ReviewTag> reviewTags, ReviewType reviewType, long writeUserId) {
        return Review.builder()
                .studyId(this.studyId)
                .targetUserId(this.targetUserId)
                .writeUserId(writeUserId)
                .reviewType(reviewType)
                .reviewTags(reviewTags)
                .content(this.content)
                .build();
    }
}
