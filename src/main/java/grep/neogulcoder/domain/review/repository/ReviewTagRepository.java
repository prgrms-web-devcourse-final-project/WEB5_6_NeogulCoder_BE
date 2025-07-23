package grep.neogulcoder.domain.review.repository;

import grep.neogulcoder.domain.review.entity.ReviewTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewTagRepository extends JpaRepository<ReviewTagEntity, Long> {
    List<ReviewTagEntity> findByReviewTagIn(List<String> reviewTags);
}
