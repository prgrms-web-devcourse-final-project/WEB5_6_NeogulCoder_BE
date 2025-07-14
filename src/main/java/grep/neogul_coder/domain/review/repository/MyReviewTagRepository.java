package grep.neogul_coder.domain.review.repository;

import grep.neogul_coder.domain.review.entity.MyReviewTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MyReviewTagRepository extends JpaRepository<MyReviewTagEntity, Long> {

    @Query("select mrt from MyReviewTagEntity mrt join fetch mrt.reviewTag rt where mrt.reviewTag.id in :reviewIds")
    List<MyReviewTagEntity> findByReviewTagIdFetchTag(List<Long> reviewIds);
}
