package grep.neogulcoder.domain.timevote.repository;

import grep.neogulcoder.domain.timevote.TimeVotePeriod;
import grep.neogulcoder.domain.timevote.TimeVoteStat;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TimeVoteStatRepository extends JpaRepository<TimeVoteStat, Long> {

  @Modifying
  @Query("UPDATE TimeVoteStat s SET s.activated = false WHERE s.period.studyId = :studyId")
  void deactivateAllByPeriod_StudyId(@Param("studyId") Long studyId);


  @Modifying
  @Query("UPDATE TimeVoteStat s SET s.activated = false WHERE s.period = :period")
  void softDeleteByPeriod(@Param("period") TimeVotePeriod period);

  @Query("SELECT s FROM TimeVoteStat s WHERE s.period.periodId = :periodId AND s.activated = true")
  List<TimeVoteStat> findAllByPeriodId(@Param("periodId") Long periodId);

  @Modifying(clearAutomatically = true)
  @Query(value = """

      INSERT INTO time_vote_stat (period_id, time_slot, vote_count, activated, created_date, modified_date)
    VALUES (:periodId, :timeSlot, :count, true, now(), now())
    ON CONFLICT (period_id, time_slot)
    DO UPDATE SET
      vote_count = time_vote_stat.vote_count + EXCLUDED.vote_count,
      modified_date = now(),
      activated = true
    """, nativeQuery = true)
  void upsertVoteStat(
      @Param("periodId") Long periodId,
      @Param("timeSlot") LocalDateTime timeSlot,
      @Param("count") Long count
  );

  @Modifying(clearAutomatically = true)
  @Query(value = """
    INSERT INTO time_vote_stat (period_id, time_slot, vote_count, activated, created_date, modified_date)
    VALUES (:periodId, :timeSlot, :voteCount, true, now(), now())
    ON CONFLICT (period_id, time_slot)
    DO UPDATE SET
      vote_count = EXCLUDED.vote_count,
      modified_date = now(),
      activated = true
    """, nativeQuery = true)
  void bulkUpsertStat(
      @Param("periodId") Long periodId,
      @Param("timeSlot") LocalDateTime timeSlot,
      @Param("voteCount") Long voteCount
  );
}
