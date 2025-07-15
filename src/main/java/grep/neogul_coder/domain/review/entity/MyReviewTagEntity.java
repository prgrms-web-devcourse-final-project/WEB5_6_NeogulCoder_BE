package grep.neogul_coder.domain.review.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class MyReviewTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private ReviewEntity reviewEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_tag_id")
    private ReviewTagEntity reviewTag;

    protected MyReviewTagEntity() {
    }

    public MyReviewTagEntity(ReviewEntity reviewEntity, ReviewTagEntity reviewTag) {
        this.reviewEntity = reviewEntity;
        this.reviewTag = reviewTag;
    }

}
