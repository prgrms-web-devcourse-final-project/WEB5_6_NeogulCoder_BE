package grep.neogulcoder.domain.timevote.repository;

import grep.neogulcoder.domain.timevote.entity.TimeVoteStat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeVoteStatRepository extends JpaRepository<TimeVoteStat, Long> {

  void deleteAllByPeriod_StudyId(Long studyId);
}
