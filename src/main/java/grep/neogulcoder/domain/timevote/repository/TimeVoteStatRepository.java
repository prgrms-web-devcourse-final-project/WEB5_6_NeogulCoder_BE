package grep.neogulcoder.domain.timevote.repository;

import grep.neogulcoder.domain.timevote.entity.TimeVotePeriod;
import grep.neogulcoder.domain.timevote.entity.TimeVoteStat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TimeVoteStatRepository extends JpaRepository<TimeVoteStat, Long> {

  void deleteAllByPeriod_StudyId(Long studyId);

  void deleteByPeriod(TimeVotePeriod period);

  @Query("SELECT s FROM TimeVoteStat s WHERE s.period.periodId = :periodId")
  List<TimeVoteStat> findAllByPeriodId(@Param("periodId") Long periodId);
}
