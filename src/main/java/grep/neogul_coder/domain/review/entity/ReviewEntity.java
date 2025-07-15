package grep.neogul_coder.domain.review.entity;

import grep.neogul_coder.domain.review.Review;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long writeUserId;
    private long targetUserId;

    @OneToMany(mappedBy = "reviewEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MyReviewTagEntity> reviewTags;

    private String content;

    protected ReviewEntity() {
    }

    @Builder
    private ReviewEntity(Review review, List<ReviewTagEntity> reviewTags) {
        this.writeUserId = review.getWriteUserId();
        this.targetUserId = review.getTargetUserId();
        this.content = review.getContent();
        this.reviewTags = reviewTags.stream()
                .map(reviewTag -> new MyReviewTagEntity(this, reviewTag))
                .toList();
    }

    public static ReviewEntity from(Review review, List<ReviewTagEntity> reviewTags) {
        return ReviewEntity.builder()
                .review(review)
                .reviewTags(reviewTags)
                .build();
    }
}
