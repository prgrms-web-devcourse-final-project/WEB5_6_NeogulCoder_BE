package grep.neogul_coder.domain.review.repository;

import grep.neogul_coder.domain.review.entity.ReviewTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewTagRepository extends JpaRepository<ReviewTagEntity, Long> {
}
