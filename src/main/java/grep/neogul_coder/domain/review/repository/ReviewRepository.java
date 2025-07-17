package grep.neogul_coder.domain.review.repository;

import grep.neogul_coder.domain.review.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findByTargetUserId(long userId);

    List<ReviewEntity> findAllByTargetUserId(Long userId);
}
