package grep.neogulcoder.domain.review.entity;

import grep.neogulcoder.domain.review.ReviewTag;
import grep.neogulcoder.domain.review.ReviewType;
import grep.neogulcoder.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "review_tag")
public class ReviewTagEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReviewType reviewType;

    private String reviewTag;

    public ReviewTagEntity(ReviewType reviewType, ReviewTag reviewTag) {
        this.reviewType = reviewType;
        this.reviewTag = reviewTag.getDescription();
    }

    protected ReviewTagEntity() {}

}
