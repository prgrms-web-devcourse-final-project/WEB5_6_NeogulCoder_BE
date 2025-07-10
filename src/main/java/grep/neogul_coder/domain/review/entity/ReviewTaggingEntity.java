package grep.neogul_coder.domain.review.entity;

import jakarta.persistence.*;

@Entity
public class ReviewTaggingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_tag_id")
    private ReviewTagEntity reviewTag;

    private long reviewId;
}
