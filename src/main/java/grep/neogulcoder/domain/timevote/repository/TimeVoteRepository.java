package grep.neogulcoder.domain.timevote.repository;

import grep.neogulcoder.domain.timevote.TimeVote;
import grep.neogulcoder.domain.timevote.TimeVotePeriod;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TimeVoteRepository extends JpaRepository<TimeVote, Long> {

  @Modifying(clearAutomatically = true)
  @Query("UPDATE TimeVote v SET v.activated = false WHERE v.period.studyId = :studyId")
  void deactivateAllByPeriod_StudyId(@Param("studyId") Long studyId);

  List<TimeVote> findByPeriodAndStudyMemberIdAndActivatedTrue(TimeVotePeriod period, Long studyMemberId);

  @Modifying(clearAutomatically = true)
  @Query("UPDATE TimeVote v SET v.activated = false WHERE v.period = :period AND v.studyMemberId = :memberId")
  void deactivateByPeriodAndStudyMember(@Param("period") TimeVotePeriod period, @Param("memberId") Long memberId);

  boolean existsByPeriodAndStudyMemberIdAndActivatedTrue(TimeVotePeriod period, Long studyMemberId);
}
