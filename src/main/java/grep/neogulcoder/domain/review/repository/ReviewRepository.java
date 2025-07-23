package grep.neogulcoder.domain.review.repository;

import grep.neogulcoder.domain.review.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findByTargetUserId(long userId);

    List<ReviewEntity> findAllByTargetUserId(Long userId);
}
