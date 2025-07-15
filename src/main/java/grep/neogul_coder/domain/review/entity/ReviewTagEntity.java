package grep.neogul_coder.domain.review.entity;

import grep.neogul_coder.domain.review.ReviewTag;
import grep.neogul_coder.domain.review.ReviewType;
import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
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
