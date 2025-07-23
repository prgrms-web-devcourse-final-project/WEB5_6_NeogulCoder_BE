package grep.neogul_coder.domain.timevote.service;

import static grep.neogul_coder.domain.timevote.exception.code.TimeVoteErrorCode.*;

import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.enums.StudyMemberRole;
import grep.neogul_coder.domain.study.repository.StudyMemberRepository;
import grep.neogul_coder.domain.study.repository.StudyRepository;
import grep.neogul_coder.domain.timevote.dto.request.TimeVotePeriodCreateRequest;
import grep.neogul_coder.domain.timevote.entity.TimeVotePeriod;
import grep.neogul_coder.domain.timevote.repository.TimeVotePeriodRepository;
import grep.neogul_coder.domain.timevote.repository.TimeVoteRepository;
import grep.neogul_coder.domain.timevote.repository.TimeVoteStatRepository;
import grep.neogul_coder.global.exception.business.BusinessException;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TimeVotePeriodService {

  private final TimeVotePeriodRepository timeVotePeriodRepository;
  private final StudyRepository studyRepository;
  private final TimeVoteRepository timeVoteRepository;
  private final TimeVoteStatRepository timeVoteStatRepository;
  private final StudyMemberRepository studyMemberRepository;

  public TimeVotePeriod createTimeVotePeriodAndReturn(TimeVotePeriodCreateRequest request, Long studyId, Long userId) {
    validateStudyLeader(studyId, userId);
    validateMaxPeriod(request.getStartDate(), request.getEndDate());

    if (timeVotePeriodRepository.existsByStudyId(studyId)) {
      deleteAllTimeVoteDate(studyId);
    }

    TimeVotePeriod savedPeriod = timeVotePeriodRepository.save(request.toEntity(studyId));

    StudyMemberRole role = getStudyMemberRole(studyId, userId);

    log.info("📝 TimeVotePeriod 생성됨 - studyId: {}, userId: {}, role: {}, periodId: {}, start: {}, end: {}",
        studyId, userId, role, savedPeriod.getPeriodId(), savedPeriod.getStartDate(), savedPeriod.getEndDate());

    return savedPeriod;
  }

  public void deleteAllTimeVoteDate(Long studyId) {
    Study study = studyRepository.findById(studyId)
        .orElseThrow(() -> new BusinessException(STUDY_NOT_FOUND));

    timeVoteRepository.deleteAllByPeriod_StudyId(studyId);
    timeVoteStatRepository.deleteAllByPeriod_StudyId(studyId);
    timeVotePeriodRepository.deleteAllByStudyId(studyId);
  }

  private void validateMaxPeriod(LocalDateTime startDate, LocalDateTime endDate) {
    Long days = Duration.between(startDate, endDate).toDays();

    if(days > 6) {
      throw new BusinessException(INVALID_TIME_VOTE_PERIOD);
    }
  }

  private void validateStudyLeader(Long studyId, Long userId) {
    boolean isLeader = studyMemberRepository.existsByStudyIdAndUserIdAndRoleAndActivatedTrue(
        studyId, userId, StudyMemberRole.LEADER
    );

    if (!isLeader) {
      throw new BusinessException(FORBIDDEN_TIME_VOTE_CREATE);
    }
  }

  private StudyMemberRole getStudyMemberRole(Long studyId, Long userId) {
    return studyMemberRepository.findByStudyIdAndUserIdAndActivatedTrue(studyId, userId)
        .map(sm -> sm.getRole())
        .orElseThrow(() -> new BusinessException(STUDY_MEMBER_NOT_FOUND));
  }
}
