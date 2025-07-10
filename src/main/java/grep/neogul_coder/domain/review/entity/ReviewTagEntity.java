package grep.neogul_coder.domain.review.entity;

import grep.neogul_coder.domain.review.ReviewType;
import jakarta.persistence.*;

@Entity
public class ReviewTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReviewType reviewType;
    private String message;

}
