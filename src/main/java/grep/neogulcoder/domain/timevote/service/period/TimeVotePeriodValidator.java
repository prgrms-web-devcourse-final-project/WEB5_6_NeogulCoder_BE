package grep.neogulcoder.domain.timevote.service.period;

import static grep.neogulcoder.domain.timevote.exception.code.TimeVoteErrorCode.*;

import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.enums.StudyMemberRole;
import grep.neogulcoder.domain.study.repository.StudyMemberRepository;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.domain.timevote.dto.request.TimeVotePeriodCreateRequest;
import grep.neogulcoder.global.exception.business.BusinessException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimeVotePeriodValidator {

  private final StudyRepository studyRepository;
  private final StudyMemberRepository studyMemberRepository;

  // ============= 주어진 요청에 대한 모든 유효성 검사를 수행한 후, 유효한 스터디 리더를 반환 =============
  public StudyMember validatePeriodCreateRequestAndReturnMember(TimeVotePeriodCreateRequest request, Long studyId, Long userId) {

    // 1. 활성화된 스터디 멤버인지 확인
    StudyMember member = getValidStudyMember(studyId, userId);
    // 2. 해당 멤버가 스터디 리더인지 확인
    validateStudyLeader(member);
    // 3. 시작일이 과거가 아닌지 확인
    validateStartDateNotPast(request.getStartDate());
    // 4. 시작일이 종료일보다 빠른지 확인
    validatePeriodRange(request.getStartDate(), request.getEndDate());
    // 5. 최대 허용 기간(7일)을 초과하지 않았는지 확인
    validateMaxPeriod(request.getStartDate(), request.getEndDate());

    return member;
  }

  protected Study getValidStudy(Long studyId) {
    return studyRepository.findById(studyId)
        .orElseThrow(() -> new BusinessException(STUDY_NOT_FOUND));
  }

  private StudyMember getValidStudyMember(Long studyId, Long userId) {
    return studyMemberRepository.findByStudyIdAndUserIdAndActivatedTrue(studyId, userId)
        .orElseThrow(() -> new BusinessException(STUDY_MEMBER_NOT_FOUND));
  }

  private void validateStudyLeader(StudyMember member){
    if(member.getRole() != StudyMemberRole.LEADER) {
      throw new BusinessException(FORBIDDEN_TIME_VOTE_CREATE);
    }
  }

  private void validateMaxPeriod(LocalDateTime startDate, LocalDateTime endDate) {
    Long days = ChronoUnit.DAYS.between(startDate.toLocalDate(), endDate.toLocalDate());

    if (days > 7) {
      throw new BusinessException(INVALID_TIME_VOTE_PERIOD);
    }
  }

  private void validatePeriodRange(LocalDateTime startDate, LocalDateTime endDate) {
    if (startDate.isAfter(endDate)) {
      throw new BusinessException(TIME_VOTE_INVALID_DATE_RANGE);
    }
  }

  private void validateStartDateNotPast(LocalDateTime startDate) {
    if (startDate.isBefore(LocalDateTime.now())) {
      throw new BusinessException(TIME_VOTE_PERIOD_START_DATE_IN_PAST);
    }
  }
}
