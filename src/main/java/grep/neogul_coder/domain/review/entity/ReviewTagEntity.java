package grep.neogul_coder.domain.review.entity;

import grep.neogul_coder.domain.review.ReviewTag;
import grep.neogul_coder.domain.review.ReviewType;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class ReviewTagEntity {

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
