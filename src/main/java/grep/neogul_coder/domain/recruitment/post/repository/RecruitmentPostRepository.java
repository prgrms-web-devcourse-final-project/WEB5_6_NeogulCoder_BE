package grep.neogul_coder.domain.recruitment.post.repository;

import grep.neogul_coder.domain.recruitment.post.RecruitmentPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RecruitmentPostRepository extends JpaRepository<RecruitmentPost, Long> {
    Optional<RecruitmentPost> findByIdAndActivatedTrue(long recruitmentPostId);

    @Modifying
    @Query("update RecruitmentPost r set r.activated = false where r.studyId = :studyId")
    void deactivateByStudyId(@Param("studyId") Long studyId);
}
