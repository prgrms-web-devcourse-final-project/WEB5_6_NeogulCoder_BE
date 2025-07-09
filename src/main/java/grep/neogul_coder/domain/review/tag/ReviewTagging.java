package grep.neogul_coder.domain.review.tag;

import jakarta.persistence.*;

public class ReviewTagging {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_tag_id")
    private ReviewTag reviewTag;

    private long reviewId;
}
