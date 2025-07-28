package grep.neogulcoder.domain.timevote.service.vote;

import grep.neogulcoder.domain.timevote.context.TimeVoteContext;
import grep.neogulcoder.domain.timevote.dto.request.TimeVoteCreateRequest;
import grep.neogulcoder.domain.timevote.dto.request.TimeVoteUpdateRequest;
import grep.neogulcoder.domain.timevote.dto.response.TimeVoteResponse;
import grep.neogulcoder.domain.timevote.dto.response.TimeVoteSubmissionStatusResponse;
import grep.neogulcoder.domain.timevote.TimeVote;
import grep.neogulcoder.domain.timevote.repository.TimeVoteRepository;
import grep.neogulcoder.domain.timevote.repository.TimeVoteQueryRepository;
import grep.neogulcoder.domain.timevote.service.TimeVoteMapper;
import grep.neogulcoder.domain.timevote.service.stat.TimeVoteStatService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TimeVoteService {

  private final TimeVoteRepository timeVoteRepository;
  private final TimeVoteQueryRepository timeVoteQueryRepository;
  private final TimeVoteStatService timeVoteStatService;
  private final TimeVoteMapper timeVoteMapper;
  private final TimeVoteValidator timeVoteValidator;

  @Transactional(readOnly = true)
  public TimeVoteResponse getMyVotes(Long studyId, Long userId) {
    // 투표 전 필요한 모든 유효성 검증 및 도메인 정보 캡슐화
    TimeVoteContext context = timeVoteValidator.getContext(studyId, userId);
    List<TimeVote> votes = timeVoteRepository.findByPeriodAndStudyMemberId(context.period(),
        context.studyMember().getId());

    return TimeVoteResponse.from(context.studyMember().getId(), votes);
  }

  public TimeVoteResponse submitVotes(TimeVoteCreateRequest request, Long studyId, Long userId) {
    // 투표 전 필요한 모든 유효성 검증 및 도메인 정보 캡슐화
    TimeVoteContext context = timeVoteValidator.getSubmitContext(studyId, userId, request.getTimeSlots());

    List<TimeVote> votes = timeVoteMapper.toEntities(request, context.period(), context.studyMember().getId());
    timeVoteRepository.saveAll(votes);

    // 통계 반영
    timeVoteStatService.incrementStats(context.period().getPeriodId(), request.getTimeSlots());

    List<TimeVote> saved = timeVoteRepository.findByPeriodAndStudyMemberId(context.period(),
        context.studyMember().getId());

    return TimeVoteResponse.from(context.studyMember().getId(), saved);
  }

  public TimeVoteResponse updateVotes(TimeVoteUpdateRequest request, Long studyId, Long userId) {
    // 투표 전 필요한 모든 유효성 검증 및 도메인 정보 캡슐화
    TimeVoteContext context = timeVoteValidator.getUpdateContext(studyId, userId, request.getTimeSlots());

    timeVoteRepository.deleteAllByPeriodAndStudyMemberId(context.period(), context.studyMember().getId());

    List<TimeVote> newVotes = timeVoteMapper.toEntities(request, context.period(), context.studyMember().getId());
    timeVoteRepository.saveAll(newVotes);

    // 통계 재반영
    timeVoteStatService.recalculateStats(context.period().getPeriodId());

    List<TimeVote> saved = timeVoteRepository.findByPeriodAndStudyMemberId(context.period(),
        context.studyMember().getId());

    return TimeVoteResponse.from(context.studyMember().getId(), saved);
  }

  public void deleteAllVotes(Long studyId, Long userId) {
    // 투표 삭제 전 필요한 모든 유효성 검증 및 도메인 정보 캡슐화
    TimeVoteContext context = timeVoteValidator.getContext(studyId, userId);

    timeVoteRepository.deleteAllByPeriodAndStudyMemberId(context.period(), context.studyMember().getId());

    timeVoteStatService.recalculateStats(context.period().getPeriodId());
  }

  public List<TimeVoteSubmissionStatusResponse> getSubmissionStatusList(Long studyId, Long userId) {
    // 사용자 제출 확인 전 필요한 모든 유효성 검증 및 도메인 정보 캡슐화
    TimeVoteContext context = timeVoteValidator.getContext(studyId, userId);

    return timeVoteQueryRepository.findSubmissionStatuses(studyId, context.period().getPeriodId());
  }
}
