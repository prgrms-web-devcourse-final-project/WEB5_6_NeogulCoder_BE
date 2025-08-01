package grep.neogulcoder.domain.recruitment.post.repository;

import grep.neogulcoder.domain.recruitment.post.RecruitmentPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RecruitmentPostRepository extends JpaRepository<RecruitmentPost, Long> {
    Optional<RecruitmentPost> findByIdAndActivatedTrue(long recruitmentPostId);

    @Modifying(clearAutomatically = true)
    @Query("update RecruitmentPost r set r.activated = false where r.studyId = :studyId")
    void deactivateByStudyId(@Param("studyId") Long studyId);

    Page<RecruitmentPost> findBySubjectContainingIgnoreCase(String subject, Pageable pageable);

    Optional<RecruitmentPost> findByStudyIdAndActivatedTrue(Long studyId);
}
