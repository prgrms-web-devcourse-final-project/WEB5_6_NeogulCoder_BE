package grep.neogulcoder.domain.timevote.service;

import static grep.neogulcoder.domain.timevote.exception.code.TimeVoteErrorCode.*;

import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.repository.StudyMemberRepository;
import grep.neogulcoder.domain.timevote.dto.response.TimeVoteStatResponse;
import grep.neogulcoder.domain.timevote.entity.TimeVotePeriod;
import grep.neogulcoder.domain.timevote.entity.TimeVoteStat;
import grep.neogulcoder.domain.timevote.repository.TimeVotePeriodRepository;
import grep.neogulcoder.domain.timevote.repository.TimeVoteStatQueryRepository;
import grep.neogulcoder.domain.timevote.repository.TimeVoteStatRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
import jakarta.persistence.OptimisticLockException;
import java.time.LocalDateTime;
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

  private final StudyMemberRepository studyMemberRepository;
  private final TimeVotePeriodRepository timeVotePeriodRepository;
  private final TimeVoteStatRepository timeVoteStatRepository;
  private final TimeVoteStatQueryRepository timeVoteStatQueryRepository;

  @Transactional(readOnly = true)
  public TimeVoteStatResponse getStats(Long studyId, Long userId) {
    getValidStudyMember(studyId, userId);
    TimeVotePeriod period = getValidTimeVotePeriodByStudyId(studyId);

    List<TimeVoteStat> stats = timeVoteStatRepository.findAllByPeriodId(period.getPeriodId());

    validateStatTimeSlotsWithinPeriod(period, stats);

    return TimeVoteStatResponse.from(period, stats);
  }

  public void incrementStats(Long periodId, List<LocalDateTime> timeSlots) {
    log.info("[TimeVoteStatService] 투표 통계 계산 시작: periodId={}, timeSlots={}", periodId, timeSlots);
    TimeVotePeriod period = getValidTimeVotePeriodByPeriodId(periodId);

    Map<LocalDateTime, Long> countMap = timeSlots.stream()
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    countMap.forEach((slot, count) -> {
      boolean success = false;
      int retry = 0;

      while (!success && retry < 3) {
        try {
          timeVoteStatQueryRepository.incrementOrInsert(period, slot, count);
          success = true;
        } catch (OptimisticLockException e) {
          retry++;
          log.warn("낙관적 락 충돌 발생: slot={} 재시도 {}/3", slot, retry); // 통계 동시성 충돌 시 재시도 (최대 3회)
          try {
            Thread.sleep(10L);
          } catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            throw new BusinessException(TIME_VOTE_THREAD_INTERRUPTED);
          }
        }
      }

      if (!success) {
        throw new BusinessException(TIME_VOTE_STAT_CONFLICT);
      }
    });
  }

  public void recalculateStats(Long periodId) {
    log.info("[TimeVoteStatService] 투표 통계 재계산 시작: periodId={}", periodId);
    synchronized (("recalc-lock:" + periodId).intern()) {
      TimeVotePeriod period = getValidTimeVotePeriodByPeriodId(periodId);

      timeVoteStatRepository.deleteByPeriod(period);

      List<TimeVoteStat> stats = timeVoteStatQueryRepository.countStatsByPeriod(period);

      timeVoteStatRepository.saveAll(stats);
    }
  }

  // ================================= 검증 로직 ================================
  // 투표 시 유효한 스터디 멤버인지 확인 (활성화된 멤버만 허용)
  private StudyMember getValidStudyMember(Long studyId, Long userId) {
    return studyMemberRepository.findByStudyIdAndUserIdAndActivatedTrue(studyId, userId)
        .orElseThrow(() -> new BusinessException(STUDY_MEMBER_NOT_FOUND));
  }

  // studyId 기준으로 스터디에 등록된 가장 최신의 투표 기간 정보 조회 (없으면 예외)
  private TimeVotePeriod getValidTimeVotePeriodByStudyId(Long studyId) {
    return timeVotePeriodRepository.findTopByStudyIdOrderByStartDateDesc(studyId)
        .orElseThrow(() -> new BusinessException(TIME_VOTE_PERIOD_NOT_FOUND));
  }

  // periodId 기준으로 투표 기간이 존재하는지 정보 조회
  private TimeVotePeriod getValidTimeVotePeriodByPeriodId(Long periodId) {
    return timeVotePeriodRepository.findById(periodId)
        .orElseThrow(() -> new BusinessException(TIME_VOTE_PERIOD_NOT_FOUND));
  }

  // 통계에 포함된 각 시간대가 기간 안에 들어가는지 확인
  private void validateStatTimeSlotsWithinPeriod(TimeVotePeriod period, List<TimeVoteStat> stats) {
    boolean invalid = stats.stream()
        .map(TimeVoteStat::getTimeSlot)
        .anyMatch(slot -> slot.isBefore(period.getStartDate()) || slot.isAfter(period.getEndDate()));

    if (invalid) {
      throw new BusinessException(TIME_VOTE_OUT_OF_RANGE);
    }
  }
}
