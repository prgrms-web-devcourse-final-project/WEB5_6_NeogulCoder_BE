package grep.neogul_coder.domain.review;

import grep.neogul_coder.domain.review.entity.ReviewEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class Review {

    private long studyId;
    private long targetUserId;
    private long writeUserId;
    private ReviewType reviewType;
    private List<ReviewTag> reviewTags;
    private String content;

    @Builder
    private Review(long studyId, long targetUserId, long writeUserId,
                  ReviewType reviewType, List<ReviewTag> reviewTags, String content) {
        this.studyId = studyId;
        this.targetUserId = targetUserId;
        this.writeUserId = writeUserId;
        this.reviewType = reviewType;
        this.reviewTags = reviewTags;
        this.content = content;
    }

}
