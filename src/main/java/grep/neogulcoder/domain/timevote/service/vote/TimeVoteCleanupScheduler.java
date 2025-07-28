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
    log.info("[Scheduler] Soft 삭제된 데이터 통계 재계산 시작");

    // 통계 재계산 로직 호출 (soft delete된 period 기준으로)
    List<Long> periodIds = jdbcTemplate.queryForList(
        "SELECT DISTINCT period_id FROM time_vote WHERE activated = false", Long.class
    );
    periodIds.forEach(timeVoteStatService::recalculateStats);

    log.info("[Scheduler] 통계 재계산 완료 - {}건", periodIds.size());

    int deletedVotes = jdbcTemplate.update("DELETE FROM time_vote WHERE activated = false");
    log.info("[Scheduler] Soft 삭제된 투표 {}건 실제 삭제 완료", deletedVotes);

    int deletedStats = jdbcTemplate.update("DELETE FROM time_vote_stat WHERE activated = false");
    log.info("[Scheduler] Soft 삭제된 통계 {}건 실제 삭제 완료", deletedStats);

    int deletedPeriods = jdbcTemplate.update("DELETE FROM time_vote_period WHERE activated = false");
    log.info("[Scheduler] Soft 삭제된 투표 기간 {}건 실제 삭제 완료", deletedPeriods);
  }
}
