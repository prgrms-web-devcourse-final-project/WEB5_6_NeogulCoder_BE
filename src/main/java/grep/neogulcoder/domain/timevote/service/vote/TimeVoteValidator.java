package grep.neogulcoder.domain.timevote.service.vote;

import static grep.neogulcoder.domain.timevote.exception.code.TimeVoteErrorCode.*;

import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.repository.StudyMemberRepository;
import grep.neogulcoder.domain.timevote.context.TimeVoteContext;
import grep.neogulcoder.domain.timevote.TimeVotePeriod;
import grep.neogulcoder.domain.timevote.repository.TimeVotePeriodRepository;
import grep.neogulcoder.domain.timevote.repository.TimeVoteRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimeVoteValidator {

  private final StudyMemberRepository studyMemberRepository;
  private final TimeVotePeriodRepository timeVotePeriodRepository;
  private final TimeVoteRepository timeVoteRepository;

  // ======================= 스터디 멤버 & 투표 기간 검증 건텍스트  =======================
  public TimeVoteContext getContext(Long studyId, Long userId) {

    // 1. 활성화된 스터디 멤버인지 확인
    StudyMember member = getValidStudyMember(studyId, userId);
    // 2. 유효한 시간 투표 기간이 존재하는지 확인
    TimeVotePeriod period = getValidTimeVotePeriod(studyId);

    return new TimeVoteContext(member, period);
  }

  // ================================= 제출용 검증 컨텍스트 =================================
  public TimeVoteContext getSubmitContext(Long studyId, Long userId, List<LocalDateTime> timeSlots) {

    // 1. 사용자가 아무 시간도 선택하지 않은 경우 방지
    validateNotEmptyVotes(timeSlots);
    // 2. 활성화된 스터디 멤버인지 확인
    StudyMember member = getValidStudyMember(studyId, userId);
    // 3. 유효한 시간 투표 기간이 존재하는지 확인
    TimeVotePeriod period = getValidTimeVotePeriod(studyId);
    // 4. 이미 투표를 제출한 적이 있는지 확인 (중복 제출 방지)
    validateNotAlreadySubmitted(period, member.getId());
    // 5. 투표 기간이 이미 종료되지 않았는지 확인
    validateNotExpired(period);
    // 6. 입력된 각 시간대가 기간 내에 있는지 확인
    validateVoteWithinPeriod(period, timeSlots);
    // 7. 중복된 시간대가 있는지 확인
    validateNoDuplicateTimeSlots(timeSlots);

    return new TimeVoteContext(member, period);
  }

  // ================================= 업데이트용 검증 컨텍스트 =================================
  public TimeVoteContext getUpdateContext(Long studyId, Long userId, List<LocalDateTime> timeSlots) {

    // 1. 사용자가 아무 시간도 선택하지 않은 경우 방지
    validateNotEmptyVotes(timeSlots);
    // 2. 활성화된 스터디 멤버인지 확인
    StudyMember member = getValidStudyMember(studyId, userId);
    // 3. 유효한 시간 투표 기간이 존재하는지 확인
    TimeVotePeriod period = getValidTimeVotePeriod(studyId);
    // 4. 기존에 제출한 투표가 있어야 수정 가능
    validateAlreadySubmitted(period, member.getId());
    // 5. 투표 기간이 이미 종료되지 않았는지 확인
    validateNotExpired(period);
    // 6. 입력된 각 시간대가 기간 내에 있는지 확인
    validateVoteWithinPeriod(period, timeSlots);
    // 7. 중복된 시간대가 있는지 확인
    validateNoDuplicateTimeSlots(timeSlots);

    return new TimeVoteContext(member, period);
  }

  private StudyMember getValidStudyMember(Long studyId, Long userId) {
    return studyMemberRepository.findByStudyIdAndUserIdAndActivatedTrue(studyId, userId)
        .orElseThrow(() -> new BusinessException(STUDY_MEMBER_NOT_FOUND));
  }

  private TimeVotePeriod getValidTimeVotePeriod(Long studyId) {
    return timeVotePeriodRepository.findTopByStudyIdAndActivatedTrueOrderByStartDateDesc(studyId)
        .orElseThrow(() -> new BusinessException(TIME_VOTE_PERIOD_NOT_FOUND));
  }

  private void validateVoteWithinPeriod(TimeVotePeriod period, List<LocalDateTime> dateTimes) {
    for (LocalDateTime dateTime : dateTimes) {
      if (dateTime.isBefore(period.getStartDate()) || dateTime.isAfter(period.getEndDate().with(
          LocalTime.of(23, 59, 59)))) {
        throw new BusinessException(TIME_VOTE_OUT_OF_RANGE);
      }
    }
  }

  private void validateNotEmptyVotes(List<LocalDateTime> timeSlots) {
    if (timeSlots == null || timeSlots.isEmpty()) {
      throw new BusinessException(TIME_VOTE_EMPTY);
    }
  }

  private void validateNoDuplicateTimeSlots(List<LocalDateTime> timeSlots) {
    Set<LocalDateTime> unique = new HashSet<>(timeSlots);
    if (unique.size() != timeSlots.size()) {
      throw new BusinessException(TIME_VOTE_DUPLICATED_TIME_SLOT);
    }
  }

  private void validateNotExpired(TimeVotePeriod period) {
    if (LocalDateTime.now().isAfter(period.getEndDate())) {
      throw new BusinessException(TIME_VOTE_PERIOD_EXPIRED);
    }
  }

  private void validateNotAlreadySubmitted(TimeVotePeriod period, Long studyMemberId) {
    boolean alreadySubmitted = timeVoteRepository.existsByPeriodAndStudyMemberIdAndActivatedTrue(period,
        studyMemberId);
    if (alreadySubmitted) {
      throw new BusinessException(TIME_VOTE_ALREADY_SUBMITTED);
    }
  }

  private void validateAlreadySubmitted(TimeVotePeriod period, Long studyMemberId) {
    boolean alreadySubmitted = timeVoteRepository.existsByPeriodAndStudyMemberIdAndActivatedTrue(period,
        studyMemberId);
    if (!alreadySubmitted) {
      throw new BusinessException(TIME_VOTE_NOT_FOUND);
    }
  }
}
