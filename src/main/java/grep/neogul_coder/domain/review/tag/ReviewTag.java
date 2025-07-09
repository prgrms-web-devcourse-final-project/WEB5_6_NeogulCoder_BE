package grep.neogul_coder.domain.review.tag;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ReviewTag {

    @Id
    private Long id;
    private ReviewType reviewType;
    private ReviewMessage message;
}
