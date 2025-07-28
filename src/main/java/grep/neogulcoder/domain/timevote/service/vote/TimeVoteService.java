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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
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
    log.info("[TimeVote] 사용자 투표 조회 시작 - studyId={}, userId={}", studyId, userId);
    // 투표 전 필요한 모든 유효성 검증 및 도메인 정보 캡슐화
    TimeVoteContext context = timeVoteValidator.getContext(studyId, userId);

    List<TimeVote> votes = timeVoteRepository.findByPeriodAndStudyMemberIdAndActivatedTrue(context.period(),
        context.studyMember().getId());

    log.info("[TimeVote] 사용자 ID={}의 투표 {}건 조회 완료", context.studyMember().getId(), votes.size());
    return TimeVoteResponse.from(context.studyMember().getId(), votes);
  }

  public TimeVoteResponse submitVotes(TimeVoteCreateRequest request, Long studyId, Long userId) {
    long totalStart = System.currentTimeMillis();
    log.info("[TimeVote] 투표 제출 시작 - studyId={}, userId={}, 요청 슬롯 수={}",
        studyId, userId, request.getTimeSlots().size());

    // 투표 전 필요한 모든 유효성 검증 및 도메인 정보 캡슐화
    TimeVoteContext context = timeVoteValidator.getSubmitContext(studyId, userId, request.getTimeSlots());

    long voteStart = System.currentTimeMillis();
    List<TimeVote> votes = timeVoteMapper.toEntities(request, context.period(), context.studyMember().getId());
    timeVoteRepository.saveAll(votes);
    log.info("[TimeVote] 투표 저장 완료 - {}건, {}ms 소요", votes.size(), System.currentTimeMillis() - voteStart);

    long statStart = System.currentTimeMillis();
    timeVoteStatService.incrementStats(context.period().getPeriodId(), request.getTimeSlots());
    log.info("[TimeVote] 통계 반영 완료 - {}ms 소요", System.currentTimeMillis() - statStart);

    List<TimeVote> saved = timeVoteRepository.findByPeriodAndStudyMemberIdAndActivatedTrue(context.period(), context.studyMember().getId());
    log.info("[TimeVote] 저장된 투표 재조회 완료 - {}건", saved.size());

    log.info("[TimeVote] 투표 제출 전체 완료 - 총 {}ms 소요", System.currentTimeMillis() - totalStart);

    return TimeVoteResponse.from(context.studyMember().getId(), saved);
  }

  public TimeVoteResponse updateVotes(TimeVoteUpdateRequest request, Long studyId, Long userId) {
    long totalStart = System.currentTimeMillis();
    log.info("[TimeVote] 투표 수정 시작 - studyId={}, userId={}, 요청 슬롯 수={}", studyId, userId, request.getTimeSlots().size());

    // 투표 전 필요한 모든 유효성 검증 및 도메인 정보 캡슐화
    TimeVoteContext context = timeVoteValidator.getUpdateContext(studyId, userId, request.getTimeSlots());

    long deleteStart = System.currentTimeMillis();
    timeVoteRepository.deactivateByPeriodAndStudyMember(context.period(), context.studyMember().getId());
    log.info("[TimeVote] 기존 투표 soft delete 완료 - {}ms 소요", System.currentTimeMillis() - deleteStart);

    long saveStart = System.currentTimeMillis();
    List<TimeVote> newVotes = timeVoteMapper.toEntities(request, context.period(), context.studyMember().getId());
    timeVoteRepository.saveAll(newVotes);
    log.info("[TimeVote] 새로운 투표 저장 완료 - {}건, {}ms 소요", newVotes.size(), System.currentTimeMillis() - saveStart);

    long statStart = System.currentTimeMillis();
    timeVoteStatService.recalculateStats(context.period().getPeriodId());
    log.info("[TimeVote] 통계 재계산 완료 - {}ms 소요", System.currentTimeMillis() - statStart);

    List<TimeVote> saved = timeVoteRepository.findByPeriodAndStudyMemberIdAndActivatedTrue(context.period(), context.studyMember().getId());
    log.info("[TimeVote] 저장된 투표 재조회 완료 - {}건", saved.size());

    log.info("[TimeVote] 투표 수정 전체 완료 - 총 {}ms 소요", System.currentTimeMillis() - totalStart);

    return TimeVoteResponse.from(context.studyMember().getId(), saved);
  }

  public void deleteAllVotes(Long studyId, Long userId) {
    // 투표 삭제 전 필요한 모든 유효성 검증 및 도메인 정보 캡슐화
    TimeVoteContext context = timeVoteValidator.getContext(studyId, userId);

    long start = System.currentTimeMillis();
    log.info("[TimeVote] 전체 투표 삭제 시작 - studyId={}, userId={}", studyId, userId);

    timeVoteRepository.deactivateByPeriodAndStudyMember(context.period(), context.studyMember().getId());
    log.info("[TimeVote] 삭제 완료 - {}ms 소요", System.currentTimeMillis() - start);

    start = System.currentTimeMillis();
    log.info("[TimeVote] 통계 재계산 시작 - periodId={}", context.period().getPeriodId());

    timeVoteStatService.recalculateStats(context.period().getPeriodId());
    log.info("[TimeVote] 통계 재계산 완료 - {}ms 소요", System.currentTimeMillis() - start);
  }

  public List<TimeVoteSubmissionStatusResponse> getSubmissionStatusList(Long studyId, Long userId) {
    log.info("[TimeVote] 제출 현황 조회 시작 - studyId={}, userId={}", studyId, userId);
    // 사용자 제출 확인 전 필요한 모든 유효성 검증 및 도메인 정보 캡슐화
    TimeVoteContext context = timeVoteValidator.getContext(studyId, userId);

    List<TimeVoteSubmissionStatusResponse> result =
        timeVoteQueryRepository.findSubmissionStatuses(studyId, context.period().getPeriodId());

    log.info("[TimeVote] 제출 현황 조회 완료 - 총 {}명", result.size());
    return result;
  }
}
