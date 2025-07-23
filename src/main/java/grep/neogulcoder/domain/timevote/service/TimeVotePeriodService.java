package grep.neogulcoder.domain.timevote.service;

import static grep.neogulcoder.domain.timevote.exception.code.TimeVoteErrorCode.*;

import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.enums.StudyMemberRole;
import grep.neogulcoder.domain.study.repository.StudyMemberRepository;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.domain.timevote.dto.request.TimeVotePeriodCreateRequest;
import grep.neogulcoder.domain.timevote.entity.TimeVotePeriod;
import grep.neogulcoder.domain.timevote.repository.TimeVotePeriodRepository;
import grep.neogulcoder.domain.timevote.repository.TimeVoteRepository;
import grep.neogulcoder.domain.timevote.repository.TimeVoteStatRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
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
    StudyMember studyMember = getValidStudyMember(studyId, userId);
    validateStudyLeader(studyMember);
    validateMaxPeriod(request.getStartDate(), request.getEndDate());

    if (timeVotePeriodRepository.existsByStudyId(studyId)) {
      deleteAllTimeVoteDate(studyId);
    }

    TimeVotePeriod savedPeriod = timeVotePeriodRepository.save(request.toEntity(studyId));

    log.info("📝 TimeVotePeriod 생성됨 - studyId: {}, userId: {}, role: {}, periodId: {}, start: {}, end: {}",
        studyId, userId, studyMember.getRole(), savedPeriod.getPeriodId(), savedPeriod.getStartDate(), savedPeriod.getEndDate());

    return savedPeriod;
  }

  public void deleteAllTimeVoteDate(Long studyId) {
    Study study = studyRepository.findById(studyId)
        .orElseThrow(() -> new BusinessException(STUDY_NOT_FOUND));

    timeVoteRepository.deleteAllByPeriod_StudyId(studyId);
    timeVoteStatRepository.deleteAllByPeriod_StudyId(studyId);
    timeVotePeriodRepository.deleteAllByStudyId(studyId);
  }

  // 검증 로직
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
    Long days = Duration.between(startDate, endDate).toDays();

    if(days > 6) {
      throw new BusinessException(INVALID_TIME_VOTE_PERIOD);
    }
  }
}
