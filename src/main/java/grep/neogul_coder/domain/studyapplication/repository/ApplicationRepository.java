package grep.neogul_coder.domain.studyapplication.repository;

import grep.neogul_coder.domain.studyapplication.StudyApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<StudyApplication, Long> {
    List<StudyApplication> findByRecruitmentPostId(Long recruitmentPostId);

    boolean existsByRecruitmentPostIdAndUserId(Long recruitmentPostId, Long userId);
}
