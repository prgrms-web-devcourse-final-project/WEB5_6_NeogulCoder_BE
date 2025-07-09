package grep.neogul_coder.domain.review;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Review {

    @Id
    private Long id;

    private long writeUserId;
    private long targetUserId;
    private String content;
}
