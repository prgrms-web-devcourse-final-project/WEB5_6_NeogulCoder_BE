package grep.neogul_coder.domain.timevote.service;

import static grep.neogul_coder.domain.timevote.exception.code.TimeVoteErrorCode.*;

import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.study.repository.StudyMemberRepository;
import grep.neogul_coder.domain.timevote.dto.request.TimeVoteCreateRequest;
import grep.neogul_coder.domain.timevote.dto.request.TimeVoteUpdateRequest;
import grep.neogul_coder.domain.timevote.dto.response.TimeVoteResponse;
import grep.neogul_coder.domain.timevote.dto.response.TimeVoteSubmissionStatusResponse;
import grep.neogul_coder.domain.timevote.entity.TimeVote;
import grep.neogul_coder.domain.timevote.entity.TimeVotePeriod;
import grep.neogul_coder.domain.timevote.repository.TimeVotePeriodRepository;
import grep.neogul_coder.domain.timevote.repository.TimeVoteRepository;
import grep.neogul_coder.domain.timevote.repository.TimeVoteQueryRepository;
import grep.neogul_coder.global.exception.business.BusinessException;
import java.time.LocalDateTime;
import java.util.List;
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
    validateVoteWithinPeriod(period, request.getTimeSlots());

    List<TimeVote> votes = request.toEntities(period, studyMember.getId());
    timeVoteRepository.saveAll(votes);

    // TODO : 통계 계산 (service 내부 또는 이벤트 방식)

    List<TimeVote> saved = timeVoteRepository.findByPeriodAndStudyMemberId(period,
        studyMember.getId());
    return TimeVoteResponse.from(studyMember.getId(), saved);
  }

  public TimeVoteResponse updateVotes(TimeVoteUpdateRequest request, Long studyId, Long userId) {
    StudyMember studyMember = getValidStudyMember(studyId, userId);
    TimeVotePeriod period = getValidTimeVotePeriod(studyId);

    validateVoteWithinPeriod(period, request.getTimeSlots());

    timeVoteRepository.deleteAllByPeriodAndStudyMemberId(period, studyMember.getId());

    List<TimeVote> newVotes = request.toEntities(period, studyMember.getId());
    timeVoteRepository.saveAll(newVotes);

    // TODO : 통계 계산 (service 내부 또는 이벤트 방식)

    List<TimeVote> saved = timeVoteRepository.findByPeriodAndStudyMemberId(period,
        studyMember.getId());
    return TimeVoteResponse.from(studyMember.getId(), saved);
  }

  public void deleteAllVotes(Long studyId, Long userId) {
    StudyMember studyMember = getValidStudyMember(studyId, userId);
    TimeVotePeriod period = getValidTimeVotePeriod(studyId);

    timeVoteRepository.deleteAllByPeriodAndStudyMemberId(period, studyMember.getId());

    // TODO : 통계 계산 (service 내부 또는 이벤트 방식)
  }

  public List<TimeVoteSubmissionStatusResponse> getSubmissionStatusList(Long studyId, Long userId) {
    getValidStudyMember(studyId, userId);
    TimeVotePeriod period = getValidTimeVotePeriod(studyId);

    return timeVoteQueryRepository.findSubmissionStatuses(studyId, period.getPeriodId());
  }

  // 검증 로직
  private StudyMember getValidStudyMember(Long studyId, Long userId) {
    return studyMemberRepository.findByStudyIdAndUserIdAndActivatedTrue(studyId, userId)
        .orElseThrow(() -> new BusinessException(STUDY_MEMBER_NOT_FOUND));
  }

  private TimeVotePeriod getValidTimeVotePeriod(Long studyId) {
    return timeVotePeriodRepository.findTopByStudyIdOrderByStartDateDesc(studyId)
        .orElseThrow(() -> new BusinessException(TIME_VOTE_PERIOD_NOT_FOUND));
  }

  private void validateVoteWithinPeriod(TimeVotePeriod period, List<LocalDateTime> dateTimes) {
    for (LocalDateTime dateTime : dateTimes) {
      if (dateTime.isBefore(period.getStartDate()) || dateTime.isAfter(period.getEndDate())) {
        throw new BusinessException(TIME_VOTE_OUT_OF_RANGE);
      }
    }
  }

  private void validateNotAlreadySubmitted(TimeVotePeriod period, Long studyMemberId) {
    boolean alreadySubmitted = timeVoteRepository.existsByPeriodAndStudyMemberId(period, studyMemberId);
    if (alreadySubmitted) {
      throw new BusinessException(TIME_VOTE_ALREADY_SUBMITTED);
    }
  }
}
