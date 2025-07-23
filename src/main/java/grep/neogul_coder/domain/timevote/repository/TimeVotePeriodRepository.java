package grep.neogul_coder.domain.timevote.repository;

import grep.neogul_coder.domain.timevote.entity.TimeVotePeriod;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeVotePeriodRepository extends JpaRepository<TimeVotePeriod, Long> {

  void deleteAllByStudyId(Long studyId);

  boolean existsByStudyId(Long studyId);

  Optional<TimeVotePeriod> findTopByStudyIdOrderByStartDateDesc(Long studyId);
}
