package grep.neogul_coder.domain.review.tag;

import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class ReviewTagging {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_tag_id")
    private ReviewTag reviewTag;

    private long reviewId;
}
