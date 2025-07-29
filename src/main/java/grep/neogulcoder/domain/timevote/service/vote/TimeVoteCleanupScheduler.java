package grep.neogulcoder.domain.timevote.service.vote;

import grep.neogulcoder.domain.timevote.service.stat.TimeVoteStatService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class TimeVoteCleanupScheduler {

  private final JdbcTemplate jdbcTemplate;
  private final TimeVoteStatService timeVoteStatService;

  // 매일 새벽 3시: soft delete 된 투표 실제 삭제
  @Scheduled(cron = "0 0 3 * * ?")
  public void hardDeleteDeactivatedVotes() {
    log.info("[Scheduler] Soft 삭제된 데이터 정리 시작");

    try {
      // 1. 통계 재계산 대상 조회
      List<Long> periodIds = jdbcTemplate.queryForList(
          "SELECT DISTINCT period_id FROM time_vote WHERE activated = false", Long.class
      );

      // // 2. 통계 재계산
      for (Long periodId : periodIds) {
        try {
          timeVoteStatService.recalculateStats(periodId);
        } catch (Exception e) {
          log.error("[Scheduler] 통계 재계산 실패 - periodId={}, error={}", periodId, e.getMessage(), e);
        }
      }

      log.info("[Scheduler] 통계 재계산 완료 - 총 {}건", periodIds.size());

    } catch (Exception e) {
      log.error("[Scheduler] 통계 재계산 단계 실패: {}", e.getMessage(), e);
    }
    // 3. 실제 삭제
    try {
      int deletedVotes = jdbcTemplate.update("DELETE FROM time_vote WHERE activated = false");
      log.info("[Scheduler] Soft 삭제된 투표 {}건 실제 삭제 완료", deletedVotes);
    } catch (Exception e) {
      log.error("[Scheduler] 투표 삭제 실패: {}", e.getMessage(), e);
    }

    try {
      int deletedStats = jdbcTemplate.update("DELETE FROM time_vote_stat WHERE activated = false");
      log.info("[Scheduler] Soft 삭제된 통계 {}건 실제 삭제 완료", deletedStats);
    } catch (Exception e) {
      log.error("[Scheduler] 통계 삭제 실패: {}", e.getMessage(), e);
    }

    try {
      int deletedPeriods = jdbcTemplate.update("DELETE FROM time_vote_period WHERE activated = false");
      log.info("[Scheduler] Soft 삭제된 투표 기간 {}건 실제 삭제 완료", deletedPeriods);
    } catch (Exception e) {
      log.error("[Scheduler] 투표 기간 삭제 실패: {}", e.getMessage(), e);
    }

    log.info("[Scheduler] Soft 삭제된 데이터 정리 작업 종료");
  }
}
