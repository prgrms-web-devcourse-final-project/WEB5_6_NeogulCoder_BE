package grep.neogulcoder.domain.studyapplication.repository;

import grep.neogulcoder.domain.studyapplication.StudyApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<StudyApplication, Long> {
    List<StudyApplication> findByRecruitmentPostId(Long recruitmentPostId);

    boolean existsByRecruitmentPostIdAndUserId(Long recruitmentPostId, Long userId);

    Optional<StudyApplication> findByIdAndActivatedTrue(Long applicationId);
}
