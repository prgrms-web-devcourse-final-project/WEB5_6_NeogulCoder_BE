package grep.neogul_coder.domain.recruitment.post.repository;

import grep.neogul_coder.domain.recruitment.post.RecruitmentPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecruitmentPostRepository extends JpaRepository<RecruitmentPost, Long> {
    Optional<RecruitmentPost> findByIdAndActivatedTrue(long recruitmentPostId);
}
