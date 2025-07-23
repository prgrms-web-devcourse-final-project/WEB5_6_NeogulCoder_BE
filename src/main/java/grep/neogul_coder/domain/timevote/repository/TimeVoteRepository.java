package grep.neogul_coder.domain.timevote.repository;

import grep.neogul_coder.domain.timevote.entity.TimeVote;
import grep.neogul_coder.domain.timevote.entity.TimeVotePeriod;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeVoteRepository extends JpaRepository<TimeVote, Long> {

  void deleteAllByPeriod_StudyId(Long studyId);

  List<TimeVote> findByPeriodAndStudyMemberId(TimeVotePeriod period, Long userId);

  void deleteAllByPeriodAndStudyMemberId(TimeVotePeriod period, Long studyMemberId);

  boolean existsByPeriodAndStudyMemberId(TimeVotePeriod period, Long studyMemberId);
}
