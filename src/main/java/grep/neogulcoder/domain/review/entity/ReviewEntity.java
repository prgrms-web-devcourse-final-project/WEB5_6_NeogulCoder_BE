package grep.neogulcoder.domain.review.entity;

import grep.neogulcoder.domain.review.Review;
import grep.neogulcoder.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
@Table(name = "review")
public class ReviewEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long studyId;
    private long writeUserId;
    private long targetUserId;

    @OneToMany(mappedBy = "reviewEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MyReviewTagEntity> reviewTags;

    private String content;

    protected ReviewEntity() {
    }

    @Builder
    private ReviewEntity(Review review, List<ReviewTagEntity> reviewTags, long studyId) {
        this.studyId = studyId;
        this.writeUserId = review.getWriteUserId();
        this.targetUserId = review.getTargetUserId();
        this.content = review.getContent();
        this.reviewTags = reviewTags.stream()
                .map(reviewTag -> new MyReviewTagEntity(this, reviewTag))
                .toList();
    }

    public static ReviewEntity from(Review review, List<ReviewTagEntity> reviewTags, long studyId) {
        return ReviewEntity.builder()
                .review(review)
                .reviewTags(reviewTags)
                .studyId(studyId)
                .build();
    }
}
