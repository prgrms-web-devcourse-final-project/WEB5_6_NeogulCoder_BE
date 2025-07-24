package grep.neogulcoder.domain.studyapplication.repository;

import grep.neogulcoder.domain.studyapplication.StudyApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<StudyApplication, Long> {
    List<StudyApplication> findByRecruitmentPostId(Long recruitmentPostId);

    boolean existsByRecruitmentPostIdAndUserId(Long recruitmentPostId, Long userId);

    Optional<StudyApplication> findByIdAndActivatedTrue(Long applicationId);

    @Modifying(clearAutomatically = true)
    @Query("update StudyApplication sa set sa.isRead = true where sa.recruitmentPostId = :recruitmentPostId and sa.isRead = false")
    void markAllAsReadByRecruitmentPostId(@Param("recruitmentPostId") Long recruitmentPostId);
}
