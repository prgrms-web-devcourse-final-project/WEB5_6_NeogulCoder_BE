package grep.neogulcoder.domain.timevote.service.stat;

import static grep.neogulcoder.domain.timevote.exception.code.TimeVoteErrorCode.*;

import grep.neogulcoder.domain.timevote.context.TimeVoteContext;
import grep.neogulcoder.domain.timevote.dto.response.TimeVoteStatResponse;
import grep.neogulcoder.domain.timevote.TimeVotePeriod;
import grep.neogulcoder.domain.timevote.TimeVoteStat;
import grep.neogulcoder.domain.timevote.repository.TimeVoteStatQueryRepository;
import grep.neogulcoder.domain.timevote.repository.TimeVoteStatRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
import jakarta.persistence.PersistenceException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TimeVoteStatService {

  private final TimeVoteStatRepository timeVoteStatRepository;
  private final TimeVoteStatQueryRepository timeVoteStatQueryRepository;
  private final TimeVoteStatValidator timeVoteStatValidator;

  @Transactional(readOnly = true)
  public TimeVoteStatResponse getStats(Long studyId, Long userId) {
    log.info("[통계 조회] 시작 - studyId={}, userId={}", studyId, userId);

    TimeVoteContext context = timeVoteStatValidator.getValidatedContext(studyId, userId);

    List<TimeVoteStat> stats = timeVoteStatRepository.findAllByPeriodId(context.period().getPeriodId());
    log.info("[통계 조회] 조회된 통계 개수 = {}", stats.size());

    timeVoteStatValidator.validateStatTimeSlotsWithinPeriod(context.period(), stats);

    return TimeVoteStatResponse.from(context.period(), stats);
  }

  public void incrementStats(Long periodId, List<LocalDateTime> timeSlots) {
    log.info("[TimeVoteStatService] 투표 통계 계산 시작: periodId={}, timeSlots={}", periodId, timeSlots);
    TimeVotePeriod period = timeVoteStatValidator.getValidTimeVotePeriodByPeriodId(periodId);

    Map<LocalDateTime, Long> countMap = timeSlots.stream()
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    try {
      for (Map.Entry<LocalDateTime, Long> entry : countMap.entrySet()) {
        timeVoteStatRepository.upsertVoteStat( period.getPeriodId(), entry.getKey(), entry.getValue());
      }
      log.info("[TimeVoteStatService] 투표 통계 반영 완료: periodId={}, 총 {}개의 슬롯", periodId, countMap.size());
    } catch (Exception e) {
      log.error("[TimeVoteStatService] 통계 반영 실패: periodId={}, 원인={}", periodId, e.getMessage(), e);
      throw new BusinessException(TIME_VOTE_STAT_FATAL);
    }
  }

  public void recalculateStats(Long periodId) {
    log.info("[TimeVoteStatService] 투표 통계 재계산 시작: periodId={}", periodId);
    TimeVotePeriod period = timeVoteStatValidator.getValidTimeVotePeriodByPeriodId(periodId);

    timeVoteStatRepository.softDeleteByPeriod(period);

    List<TimeVoteStat> stats = timeVoteStatQueryRepository.countStatsByPeriod(period);

    try {
      for (TimeVoteStat stat : stats) {
        timeVoteStatRepository.bulkUpsertStat(stat.getPeriod().getPeriodId(), stat.getTimeSlot(), stat.getVoteCount());
      }
      log.info("[TimeVoteStatService] 투표 통계 재계산 완료: periodId={}, 총 {}개의 슬롯", periodId, stats.size());
    } catch (DataAccessException | PersistenceException e) {
      log.error("[TimeVoteStatService] 통계 upsert 실패: periodId={}, 원인={}", periodId, e.getMessage(),
          e);
      throw new BusinessException(TIME_VOTE_STAT_FATAL);
    }
  }
}
