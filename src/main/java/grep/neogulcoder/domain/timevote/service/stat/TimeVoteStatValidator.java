package grep.neogulcoder.domain.timevote.service.stat;

import static grep.neogulcoder.domain.timevote.exception.code.TimeVoteErrorCode.*;
import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.repository.StudyMemberRepository;
import grep.neogulcoder.domain.timevote.context.TimeVoteContext;
import grep.neogulcoder.domain.timevote.TimeVotePeriod;
import grep.neogulcoder.domain.timevote.TimeVoteStat;
import grep.neogulcoder.domain.timevote.repository.TimeVotePeriodRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimeVoteStatValidator {

  private final StudyMemberRepository studyMemberRepository;
  private final TimeVotePeriodRepository timeVotePeriodRepository;

  // ======================= 스터디 멤버 & 투표 기간 검증 건텍스트  =======================
  public TimeVoteContext getValidatedContext(Long studyId, Long userId) {

    // 1. 활성화된 스터디 멤버인지 확인
    StudyMember member = getValidStudyMember(studyId, userId);
    // 2. 유효한 시간 투표 기간이 존재하는지 확인
    TimeVotePeriod period = getValidTimeVotePeriodByStudyId(studyId);

    return new TimeVoteContext(member, period);
  }

  // periodId 기준으로 투표 기간이 존재하는지 정보 조회
  protected TimeVotePeriod getValidTimeVotePeriodByPeriodId(Long periodId) {
    return timeVotePeriodRepository.findById(periodId)
        .orElseThrow(() -> new BusinessException(TIME_VOTE_PERIOD_NOT_FOUND));
  }

  // 통계에 포함된 각 시간대가 기간 안에 들어가는지 확인
  protected void validateStatTimeSlotsWithinPeriod(TimeVotePeriod period, List<TimeVoteStat> stats) {
    boolean invalid = stats.stream()
        .map(TimeVoteStat::getTimeSlot)
        .anyMatch(slot -> slot.isBefore(period.getStartDate()) || slot.isAfter(period.getEndDate()));

    if (invalid) {
      throw new BusinessException(TIME_VOTE_OUT_OF_RANGE);
    }
  }

  private StudyMember getValidStudyMember(Long studyId, Long userId) {
    return studyMemberRepository.findByStudyIdAndUserIdAndActivatedTrue(studyId, userId)
        .orElseThrow(() -> new BusinessException(STUDY_MEMBER_NOT_FOUND));
  }

  private TimeVotePeriod getValidTimeVotePeriodByStudyId(Long studyId) {
    return timeVotePeriodRepository.findTopByStudyIdOrderByStartDateDesc(studyId)
        .orElseThrow(() -> new BusinessException(TIME_VOTE_PERIOD_NOT_FOUND));
  }
}
