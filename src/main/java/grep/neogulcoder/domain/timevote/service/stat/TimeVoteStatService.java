package grep.neogulcoder.domain.timevote.service.stat;

import static grep.neogulcoder.domain.timevote.exception.code.TimeVoteErrorCode.*;

import grep.neogulcoder.domain.timevote.context.TimeVoteContext;
import grep.neogulcoder.domain.timevote.dto.response.TimeVoteStatResponse;
import grep.neogulcoder.domain.timevote.TimeVotePeriod;
import grep.neogulcoder.domain.timevote.TimeVoteStat;
import grep.neogulcoder.domain.timevote.repository.TimeVoteStatQueryRepository;
import grep.neogulcoder.domain.timevote.repository.TimeVoteStatRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
import jakarta.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    TimeVoteContext context = timeVoteStatValidator.getValidatedContext(studyId, userId);

    List<TimeVoteStat> stats = timeVoteStatRepository.findAllByPeriodId(context.period().getPeriodId());

    timeVoteStatValidator.validateStatTimeSlotsWithinPeriod(context.period(), stats);

    return TimeVoteStatResponse.from(context.period(), stats);
  }

  public void incrementStats(Long periodId, List<LocalDateTime> timeSlots) {
    log.info("[TimeVoteStatService] 투표 통계 계산 시작: periodId={}, timeSlots={}", periodId, timeSlots);
    TimeVotePeriod period = timeVoteStatValidator.getValidTimeVotePeriodByPeriodId(periodId);

    Map<LocalDateTime, Long> countMap = timeSlots.stream()
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    List<LocalDateTime> failedSlots = new ArrayList<>();

    countMap.forEach((slot, count) -> {
      boolean success = false;
      int retry = 0;

      while (!success && retry < 3) {
        try {
          timeVoteStatQueryRepository.incrementOrInsert(period, slot, count);
          success = true;
        } catch (OptimisticLockException e) {
          retry++;
          // 현재 version 로그는 트랜잭션 커밋 시점에 확인 필요
          log.warn("[TimeVoteStatService] 낙관적 락 충돌 발생: periodId={}, slot={}, count={}, 재시도 {}/3, 원인={}",
              period.getPeriodId(), slot, count, retry, e.getMessage()); // 통계 동시성 충돌 시 재시도 (최대 3회)
          try {
            Thread.sleep(10L);
          } catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            throw new BusinessException(TIME_VOTE_THREAD_INTERRUPTED);
          }
        }
      }

      if (!success) {
        failedSlots.add(slot); // 실패한 slot 따로 저장
      }
    });

    if (!failedSlots.isEmpty()) {
      log.error("[TimeVoteStatService] 통계 반영 실패: periodId={}, 실패 slot 목록={}", periodId, failedSlots);
      throw new BusinessException(TIME_VOTE_STAT_CONFLICT);
    }
  }

  public void recalculateStats(Long periodId) {
    log.info("[TimeVoteStatService] 투표 통계 재계산 시작: periodId={}", periodId);
    try {
      synchronized (("recalc-lock:" + periodId).intern()) {
        TimeVotePeriod period = timeVoteStatValidator.getValidTimeVotePeriodByPeriodId(periodId);

        timeVoteStatRepository.deleteByPeriod(period);

        List<TimeVoteStat> stats = timeVoteStatQueryRepository.countStatsByPeriod(period);

        timeVoteStatRepository.saveAll(stats);

        log.info("[TimeVoteStatService] 통계 재계산 완료: 저장된 slot 수 = {}", stats.size());
      }
    } catch (Exception e) {
      log.error("[TimeVoteStatService] 통계 재계산 실패: periodId={}, 원인={}", periodId, e.getMessage(), e);
      throw new BusinessException(TIME_VOTE_STAT_FATAL);
    }
  }
}
