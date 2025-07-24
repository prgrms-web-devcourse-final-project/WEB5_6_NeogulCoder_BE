package grep.neogulcoder.domain.timevote.service;

import static grep.neogulcoder.domain.timevote.exception.code.TimeVoteErrorCode.*;

import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.repository.StudyMemberRepository;
import grep.neogulcoder.domain.timevote.dto.request.TimeVoteCreateRequest;
import grep.neogulcoder.domain.timevote.dto.request.TimeVoteUpdateRequest;
import grep.neogulcoder.domain.timevote.dto.response.TimeVoteResponse;
import grep.neogulcoder.domain.timevote.dto.response.TimeVoteSubmissionStatusResponse;
import grep.neogulcoder.domain.timevote.entity.TimeVote;
import grep.neogulcoder.domain.timevote.entity.TimeVotePeriod;
import grep.neogulcoder.domain.timevote.repository.TimeVotePeriodRepository;
import grep.neogulcoder.domain.timevote.repository.TimeVoteRepository;
import grep.neogulcoder.domain.timevote.repository.TimeVoteQueryRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TimeVoteService {

  private final StudyMemberRepository studyMemberRepository;
  private final TimeVotePeriodRepository timeVotePeriodRepository;
  private final TimeVoteRepository timeVoteRepository;
  private final TimeVoteQueryRepository timeVoteQueryRepository;
  private final TimeVoteStatService timeVoteStatService;

  @Transactional(readOnly = true)
  public TimeVoteResponse getMyVotes(Long studyId, Long userId) {
    StudyMember studyMember = getValidStudyMember(studyId, userId);
    TimeVotePeriod period = getValidTimeVotePeriod(studyId);

    List<TimeVote> votes = timeVoteRepository.findByPeriodAndStudyMemberId(period,
        studyMember.getId());
    return TimeVoteResponse.from(studyMember.getId(), votes);
  }

  public TimeVoteResponse submitVotes(TimeVoteCreateRequest request, Long studyId, Long userId) {
    StudyMember studyMember = getValidStudyMember(studyId, userId);
    TimeVotePeriod period = getValidTimeVotePeriod(studyId);

    validateNotAlreadySubmitted(period, studyMember.getId());
    validateNotExpired(period);

    validateNotEmptyVotes(request.getTimeSlots());
    validateVoteWithinPeriod(period, request.getTimeSlots());
    validateNoDuplicateTimeSlots(request.getTimeSlots());

    List<TimeVote> votes = request.toEntities(period, studyMember.getId());
    timeVoteRepository.saveAll(votes);

    timeVoteStatService.incrementStats(period.getPeriodId(), request.getTimeSlots());

    List<TimeVote> saved = timeVoteRepository.findByPeriodAndStudyMemberId(period,
        studyMember.getId());
    return TimeVoteResponse.from(studyMember.getId(), saved);
  }

  public TimeVoteResponse updateVotes(TimeVoteUpdateRequest request, Long studyId, Long userId) {
    StudyMember studyMember = getValidStudyMember(studyId, userId);
    TimeVotePeriod period = getValidTimeVotePeriod(studyId);

    validateAlreadySubmitted(period, studyMember.getId());
    validateNotExpired(period);

    validateNotEmptyVotes(request.getTimeSlots());
    validateVoteWithinPeriod(period, request.getTimeSlots());
    validateNoDuplicateTimeSlots(request.getTimeSlots());

    timeVoteRepository.deleteAllByPeriodAndStudyMemberId(period, studyMember.getId());

    List<TimeVote> newVotes = request.toEntities(period, studyMember.getId());
    timeVoteRepository.saveAll(newVotes);

    timeVoteStatService.recalculateStats(period.getPeriodId());

    List<TimeVote> saved = timeVoteRepository.findByPeriodAndStudyMemberId(period,
        studyMember.getId());
    return TimeVoteResponse.from(studyMember.getId(), saved);
  }

  public void deleteAllVotes(Long studyId, Long userId) {
    StudyMember studyMember = getValidStudyMember(studyId, userId);
    TimeVotePeriod period = getValidTimeVotePeriod(studyId);

    timeVoteRepository.deleteAllByPeriodAndStudyMemberId(period, studyMember.getId());

    timeVoteStatService.recalculateStats(period.getPeriodId());
  }

  public List<TimeVoteSubmissionStatusResponse> getSubmissionStatusList(Long studyId, Long userId) {
    getValidStudyMember(studyId, userId);
    TimeVotePeriod period = getValidTimeVotePeriod(studyId);

    return timeVoteQueryRepository.findSubmissionStatuses(studyId, period.getPeriodId());
  }

  // ================================= 검증 로직 ================================
  // 투표 시 유효한 스터디 멤버인지 확인 (활성화된 멤버만 허용)
  private StudyMember getValidStudyMember(Long studyId, Long userId) {
    return studyMemberRepository.findByStudyIdAndUserIdAndActivatedTrue(studyId, userId)
        .orElseThrow(() -> new BusinessException(STUDY_MEMBER_NOT_FOUND));
  }

  // 스터디에 등록된 가장 최신의 투표 기간 정보 조회 (없으면 예외)
  private TimeVotePeriod getValidTimeVotePeriod(Long studyId) {
    return timeVotePeriodRepository.findTopByStudyIdOrderByStartDateDesc(studyId)
        .orElseThrow(() -> new BusinessException(TIME_VOTE_PERIOD_NOT_FOUND));
  }

  // 각 투표 시간이 투표 기간 내에 포함되는지 확인
  private void validateVoteWithinPeriod(TimeVotePeriod period, List<LocalDateTime> dateTimes) {
    for (LocalDateTime dateTime : dateTimes) {
      if (dateTime.isBefore(period.getStartDate()) || dateTime.isAfter(period.getEndDate())) {
        throw new BusinessException(TIME_VOTE_OUT_OF_RANGE);
      }
    }
  }

  // 이미 투표한 적이 있는지 확인 (중복 투표 방지용)
  private void validateNotAlreadySubmitted(TimeVotePeriod period, Long studyMemberId) {
    boolean alreadySubmitted = timeVoteRepository.existsByPeriodAndStudyMemberId(period, studyMemberId);
    if (alreadySubmitted) {
      throw new BusinessException(TIME_VOTE_ALREADY_SUBMITTED);
    }
  }

  // 업데이트 시 기존에 투표한 적이 있는지 확인 (없는 경우 업데이트 불가)
  private void validateAlreadySubmitted(TimeVotePeriod period, Long studyMemberId) {
    boolean alreadySubmitted = timeVoteRepository.existsByPeriodAndStudyMemberId(period, studyMemberId);
    if (!alreadySubmitted) {
      throw new BusinessException(TIME_VOTE_NOT_FOUND);
    }
  }

  // 이미 제출한 시간에 중복 투표를 시도했는지 확인
  private void validateNoDuplicateTimeSlots(List<LocalDateTime> timeSlots) {
    Set<LocalDateTime> unique = new HashSet<>(timeSlots);
    if (unique.size() != timeSlots.size()) {
      throw new BusinessException(TIME_VOTE_DUPLICATED_TIME_SLOT);
    }
  }

  // 투표 기간이 이미 만료되었는지 확인 (종료일 이후 투표 금지)
  private void validateNotExpired(TimeVotePeriod period) {
    if (LocalDateTime.now().isAfter(period.getEndDate())) {
      throw new BusinessException(TIME_VOTE_PERIOD_EXPIRED);
    }
  }

  // 입력된 투표 시간이 비어 있는지 확인 (아예 투표하지 않은 경우)
  private void validateNotEmptyVotes(List<LocalDateTime> timeSlots) {
    if (timeSlots == null || timeSlots.isEmpty()) {
      throw new BusinessException(TIME_VOTE_EMPTY);
    }
  }
}
