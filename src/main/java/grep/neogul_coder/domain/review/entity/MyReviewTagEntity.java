package grep.neogul_coder.domain.review.entity;

import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "my_review_tag")
public class MyReviewTagEntity extends BaseEntity {

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
