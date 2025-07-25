package grep.neogulcoder.domain.timevote.service;

import static grep.neogulcoder.domain.timevote.exception.code.TimeVoteErrorCode.FORBIDDEN_TIME_VOTE_CREATE;
import static grep.neogulcoder.domain.timevote.exception.code.TimeVoteErrorCode.INVALID_TIME_VOTE_PERIOD;
import static grep.neogulcoder.domain.timevote.exception.code.TimeVoteErrorCode.STUDY_MEMBER_NOT_FOUND;
import static grep.neogulcoder.domain.timevote.exception.code.TimeVoteErrorCode.STUDY_NOT_FOUND;
import static grep.neogulcoder.domain.timevote.exception.code.TimeVoteErrorCode.TIME_VOTE_INVALID_DATE_RANGE;
import static grep.neogulcoder.domain.timevote.exception.code.TimeVoteErrorCode.TIME_VOTE_PERIOD_START_DATE_IN_PAST;

import grep.neogulcoder.domain.alram.service.AlarmService;
import grep.neogulcoder.domain.alram.type.AlarmType;
import grep.neogulcoder.domain.alram.type.DomainType;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.enums.StudyMemberRole;
import grep.neogulcoder.domain.study.repository.StudyMemberRepository;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.domain.timevote.dto.request.TimeVotePeriodCreateRequest;
import grep.neogulcoder.domain.timevote.dto.response.TimeVotePeriodResponse;
import grep.neogulcoder.domain.timevote.entity.TimeVotePeriod;
import grep.neogulcoder.domain.timevote.repository.TimeVotePeriodRepository;
import grep.neogulcoder.domain.timevote.repository.TimeVoteRepository;
import grep.neogulcoder.domain.timevote.repository.TimeVoteStatRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
import grep.neogulcoder.global.provider.finder.MessageFinder;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TimeVotePeriodService {

  private final TimeVotePeriodRepository timeVotePeriodRepository;
  private final TimeVoteRepository timeVoteRepository;
  private final TimeVoteStatRepository timeVoteStatRepository;
  private final StudyRepository studyRepository;
  private final StudyMemberRepository studyMemberRepository;
  private final AlarmService alarmService;
  private final MessageFinder messageFinder;

  public TimeVotePeriodResponse createTimeVotePeriodAndReturn(TimeVotePeriodCreateRequest request, Long studyId, Long userId) {
    StudyMember studyMember = getValidStudyMember(studyId, userId);
    validateStudyLeader(studyMember);

    validateStartDateNotPast(request.getStartDate());
    validatePeriodRange(request.getStartDate(), request.getEndDate());
    validateMaxPeriod(request.getStartDate(), request.getEndDate());

    LocalDateTime adjustedEndDate = adjustEndDate(request.getEndDate());

    if (timeVotePeriodRepository.existsByStudyId(studyId)) {
      deleteAllTimeVoteDate(studyId);
    }

    TimeVotePeriod savedPeriod = timeVotePeriodRepository.save(request.toEntity(studyId, adjustedEndDate));

    getValidStudy(studyId);
    messageFinder.findMessage(AlarmType.TIME_VOTE_REQUEST, DomainType.TIME_VOTE, studyId);

    List<StudyMember> members = studyMemberRepository.findAllByStudyIdAndActivatedTrue(studyId);
    List<Long> notifiedUserIds = new ArrayList<>();
    for (StudyMember member : members) {
      if (!member.getUserId().equals(userId)) {
        alarmService.saveAlarm(member.getUserId(), AlarmType.TIME_VOTE_REQUEST,
            DomainType.TIME_VOTE, studyId);
        notifiedUserIds.add(member.getId());
      }
    }

    return TimeVotePeriodResponse.from(savedPeriod, notifiedUserIds);
  }

  public void deleteAllTimeVoteDate(Long studyId) {
    getValidStudy(studyId);

    timeVoteRepository.deleteAllByPeriod_StudyId(studyId);
    timeVoteStatRepository.deleteAllByPeriod_StudyId(studyId);
    timeVotePeriodRepository.deleteAllByStudyId(studyId);
  }

  // ================================= 검증 로직 ================================
  // 주어진 ID의 스터디가 존재하는지 검증 (존재하지 않으면 예외 발생)
  private Study getValidStudy(Long studyId) {
    return studyRepository.findById(studyId)
        .orElseThrow(() -> new BusinessException(STUDY_NOT_FOUND));
  }

  // 유효한 스터디 멤버인지 확인 (활성화된 멤버만 허용)
  private StudyMember getValidStudyMember(Long studyId, Long userId) {
    return studyMemberRepository.findByStudyIdAndUserIdAndActivatedTrue(studyId, userId)
        .orElseThrow(() -> new BusinessException(STUDY_MEMBER_NOT_FOUND));
  }

  // 요청자가 스터디장(LEADER)인지 확인 (스터디장만 투표 기간 생성 가능)
  private void validateStudyLeader(StudyMember member){
    if(member.getRole() != StudyMemberRole.LEADER) {
      throw new BusinessException(FORBIDDEN_TIME_VOTE_CREATE);
    }
  }

  // 투표 가능 기간은 최대 7일로 제한
  private void validateMaxPeriod(LocalDateTime startDate, LocalDateTime endDate) {
    Long days = ChronoUnit.DAYS.between(startDate.toLocalDate(), endDate.toLocalDate());

    if (days > 7) {
      throw new BusinessException(INVALID_TIME_VOTE_PERIOD);
    }
  }

  // 투표 기간 종료일을 마지막 날 23:59:59로 보정
  private LocalDateTime adjustEndDate(LocalDateTime endDate) {
    return endDate.with(LocalTime.of(23, 59, 59));
  }

  // 시작일이 종료일보다 늦은 비정상적인 입력 방지
  private void validatePeriodRange(LocalDateTime startDate, LocalDateTime endDate) {
    if (startDate.isAfter(endDate)) {
      throw new BusinessException(TIME_VOTE_INVALID_DATE_RANGE);
    }
  }

  // 시작일이 현재보다 과거이면 예외
  private void validateStartDateNotPast(LocalDateTime startDate) {
    if (startDate.isBefore(LocalDateTime.now())) {
      throw new BusinessException(TIME_VOTE_PERIOD_START_DATE_IN_PAST);
    }
  }
}
