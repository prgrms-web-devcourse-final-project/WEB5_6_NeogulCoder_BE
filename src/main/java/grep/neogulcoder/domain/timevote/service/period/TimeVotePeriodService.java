package grep.neogulcoder.domain.timevote.service.period;

import static grep.neogulcoder.domain.timevote.exception.code.TimeVoteErrorCode.*;

import grep.neogulcoder.domain.timevote.dto.request.TimeVotePeriodCreateRequest;
import grep.neogulcoder.domain.timevote.dto.response.TimeVotePeriodResponse;
import grep.neogulcoder.domain.timevote.TimeVotePeriod;
import grep.neogulcoder.domain.timevote.event.TimeVotePeriodCreatedEvent;
import grep.neogulcoder.domain.timevote.repository.TimeVotePeriodRepository;
import grep.neogulcoder.domain.timevote.repository.TimeVoteRepository;
import grep.neogulcoder.domain.timevote.repository.TimeVoteStatRepository;
import grep.neogulcoder.domain.timevote.service.TimeVoteMapper;
import grep.neogulcoder.global.exception.business.BusinessException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TimeVotePeriodService {

  private final TimeVotePeriodRepository timeVotePeriodRepository;
  private final TimeVoteRepository timeVoteRepository;
  private final TimeVoteStatRepository timeVoteStatRepository;
  private final TimeVotePeriodValidator timeVotePeriodValidator;
  private final TimeVoteMapper timeVoteMapper;
  private final ApplicationEventPublisher eventPublisher;

  public TimeVotePeriodResponse createTimeVotePeriodAndReturn(TimeVotePeriodCreateRequest request, Long studyId, Long userId) {
    log.info("[TimeVotePeriod] 투표 기간 생성 시작 - studyId={}, userId={}, 요청={}", studyId, userId, request);

    // 입력값 검증 (스터디 존재, 멤버 활성화 여부, 리더 여부, 날짜 유효성 등)
    timeVotePeriodValidator.validatePeriodCreateRequestAndReturnMember(request, studyId, userId);
    LocalDateTime adjustedEndDate = adjustEndDate(request.getEndDate());

    try {
      if (timeVotePeriodRepository.existsByStudyIdAndActivatedTrue(studyId)) {
        log.info("[TimeVotePeriod] 기존 투표 기간 존재, 전체 투표 데이터 삭제 진행");
        deleteAllTimeVoteDate(studyId);
      }
    } catch (Exception e) {
      log.error("[TimeVotePeriod] 기존 투표 삭제 중 오류 발생 - studyId={}, error={}", studyId, e.getMessage(), e);
      throw new BusinessException(TIME_VOTE_DELETE_FAILED);
    }

    TimeVotePeriod savedPeriod = timeVotePeriodRepository.save(timeVoteMapper.toEntity(request, studyId, adjustedEndDate));
    log.info("[TimeVotePeriod] 투표 기간 저장 완료 - periodId={}", savedPeriod.getPeriodId());

    // 알림 메시지를 위한 스터디 유효성 검증 (존재 확인)
    timeVotePeriodValidator.getValidStudy(studyId);

    // 리더 자신을 제외한 나머지 멤버들에게 투표 요청 알림 저장
    eventPublisher.publishEvent(new TimeVotePeriodCreatedEvent(studyId, userId));
    log.info("[TimeVotePeriod] 투표 요청 알림 이벤트 발행 완료 - studyId={}, userId={}", studyId, userId);
    return TimeVotePeriodResponse.from(savedPeriod);
  }

  // 해당 스터디 ID로 등록된 모든 투표 관련 데이터 삭제
  public void deleteAllTimeVoteDate(Long studyId) {
    long start = System.currentTimeMillis();
    log.info("[TimeVotePeriod] 전체 투표 데이터 삭제 시작 - studyId={}", studyId);

    timeVotePeriodValidator.getValidStudy(studyId);

    timeVoteRepository.deactivateAllByPeriod_StudyId(studyId);
    log.info("[TimeVotePeriod] 투표 soft delete 완료");

    timeVoteStatRepository.deactivateAllByPeriod_StudyId(studyId);
    log.info("[TimeVotePeriod] 통계 soft delete 완료");

    timeVotePeriodRepository.deactivateAllByStudyId(studyId);
    log.info("[TimeVotePeriod] 투표 기간 hard delete 완료");

    log.info("[TimeVotePeriod] 전체 삭제 완료 - {}ms 소요", System.currentTimeMillis() - start);
  }

  // 투표 기간의 종료일을 '해당 일의 23:59:59'로 보정
  private LocalDateTime adjustEndDate(LocalDateTime endDate) {
    return endDate.with(LocalTime.of(23, 59, 59));
  }
}
