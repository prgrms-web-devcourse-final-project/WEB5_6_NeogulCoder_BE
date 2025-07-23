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
    TimeVotePeriod period = getValidTimeVotePeriod(studyId);
    StudyMember studyMember = getvalidateStudyMember(studyId, userId);

    List<TimeVote> votes = timeVoteRepository.findByPeriodAndStudyMemberId(period,
        studyMember.getId());
    return TimeVoteResponse.from(studyMember.getId(), votes);
  }

  public TimeVoteResponse submitVotes(TimeVoteCreateRequest request, Long studyId, Long userId) {
    TimeVotePeriod period = getValidTimeVotePeriod(studyId);
    StudyMember studyMember = getvalidateStudyMember(studyId, userId);

    List<TimeVote> votes = request.toEntities(period, studyMember.getId());
    timeVoteRepository.saveAll(votes);

    // TODO : 통계 계산 (service 내부 또는 이벤트 방식)

    List<TimeVote> saved = timeVoteRepository.findByPeriodAndStudyMemberId(period,
        studyMember.getId());
    return TimeVoteResponse.from(studyMember.getId(), saved);
  }

  public TimeVoteResponse updateVote(TimeVoteUpdateRequest request, Long studyId, Long userId) {
    TimeVotePeriod period = getValidTimeVotePeriod(studyId);
    StudyMember studyMember = getvalidateStudyMember(studyId, userId);

    timeVoteRepository.deleteAllByPeriodAndStudyMemberId(period, studyMember.getId());

    List<TimeVote> newVotes = request.toEntities(period, studyMember.getId());
    timeVoteRepository.saveAll(newVotes);

    // TODO : 통계 계산 (service 내부 또는 이벤트 방식)

    List<TimeVote> saved = timeVoteRepository.findByPeriodAndStudyMemberId(period,
        studyMember.getId());
    return TimeVoteResponse.from(studyMember.getId(), saved);
  }

  public void deleteAllVotes(Long studyId, Long userId) {
    TimeVotePeriod period = getValidTimeVotePeriod(studyId);
    StudyMember studyMember = getvalidateStudyMember(studyId, userId);

    timeVoteRepository.deleteAllByPeriodAndStudyMemberId(period, studyMember.getId());

    // TODO : 통계 계산 (service 내부 또는 이벤트 방식)
  }

  public List<TimeVoteSubmissionStatusResponse> getSubmissionStatusList(Long studyId, Long userId) {
    TimeVotePeriod period = getValidTimeVotePeriod(studyId);
    validateStudyMember(studyId, userId);

    return timeVoteQueryRepository.findSubmissionStatuses(studyId, period.getPeriodId());
  }

  private TimeVotePeriod getValidTimeVotePeriod(Long studyId) {
    return timeVotePeriodRepository.findTopByStudyIdOrderByStartDateDesc(studyId)
        .orElseThrow(() -> new BusinessException(TIME_VOTE_PERIOD_NOT_FOUND));
  }

  private StudyMember getvalidateStudyMember(Long studyId, Long userId) {
    return studyMemberRepository.findByStudyIdAndUserIdAndActivatedTrue(studyId, userId)
        .orElseThrow(() -> new BusinessException(STUDY_MEMBER_NOT_FOUND));
  }

  private void validateStudyMember(Long studyId, Long userId) {
    boolean isMember = studyMemberRepository.existsByStudyIdAndUserIdAndActivatedTrue(studyId, userId);
    if (!isMember) {
      throw new BusinessException(STUDY_MEMBER_NOT_FOUND);
    }
  }
}
