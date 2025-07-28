package grep.neogulcoder.domain.timevote.service.period;

import grep.neogulcoder.domain.timevote.dto.request.TimeVotePeriodCreateRequest;
import grep.neogulcoder.domain.timevote.dto.response.TimeVotePeriodResponse;
import grep.neogulcoder.domain.timevote.TimeVotePeriod;
import grep.neogulcoder.domain.timevote.event.TimeVotePeriodCreatedEvent;
import grep.neogulcoder.domain.timevote.repository.TimeVotePeriodRepository;
import grep.neogulcoder.domain.timevote.repository.TimeVoteRepository;
import grep.neogulcoder.domain.timevote.repository.TimeVoteStatRepository;
import grep.neogulcoder.domain.timevote.service.TimeVoteMapper;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    // 입력값 검증 (스터디 존재, 멤버 활성화 여부, 리더 여부, 날짜 유효성 등)
    timeVotePeriodValidator.validatePeriodCreateRequestAndReturnMember(request, studyId, userId);

    LocalDateTime adjustedEndDate = adjustEndDate(request.getEndDate());

    if (timeVotePeriodRepository.existsByStudyId(studyId)) { deleteAllTimeVoteDate(studyId); }

    TimeVotePeriod savedPeriod = timeVotePeriodRepository.save(timeVoteMapper.toEntity(request, studyId, adjustedEndDate));

    // 알림 메시지를 위한 스터디 유효성 검증 (존재 확인)
    timeVotePeriodValidator.getValidStudy(studyId);

    // 리더 자신을 제외한 나머지 멤버들에게 투표 요청 알림 저장
    eventPublisher.publishEvent(new TimeVotePeriodCreatedEvent(studyId, userId));
    return TimeVotePeriodResponse.from(savedPeriod);
  }

  // 해당 스터디 ID로 등록된 모든 투표 관련 데이터 삭제
  public void deleteAllTimeVoteDate(Long studyId) {
    timeVotePeriodValidator.getValidStudy(studyId);

    timeVoteRepository.deleteAllByPeriod_StudyId(studyId);
    timeVoteStatRepository.deleteAllByPeriod_StudyId(studyId);
    timeVotePeriodRepository.deleteAllByStudyId(studyId);
  }

  // 투표 기간의 종료일을 '해당 일의 23:59:59'로 보정
  private LocalDateTime adjustEndDate(LocalDateTime endDate) {
    return endDate.with(LocalTime.of(23, 59, 59));
  }
}
