package grep.neogulcoder.domain.timevote.repository;

import grep.neogulcoder.domain.timevote.TimeVotePeriod;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TimeVotePeriodRepository extends JpaRepository<TimeVotePeriod, Long> {

  @Modifying
  @Query("UPDATE TimeVotePeriod p SET p.activated = false WHERE p.studyId = :studyId")
  void deactivateAllByStudyId(@Param("studyId") Long studyId);

  boolean existsByStudyIdAndActivatedTrue(Long studyId);

  Optional<TimeVotePeriod> findTopByStudyIdAndActivatedTrueOrderByStartDateDesc(Long studyId);
}
