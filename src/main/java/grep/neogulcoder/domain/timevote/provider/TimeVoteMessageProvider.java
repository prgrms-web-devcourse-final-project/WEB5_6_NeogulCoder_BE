package grep.neogulcoder.domain.timevote.provider;

import static grep.neogulcoder.domain.alram.exception.code.AlarmErrorCode.*;
import static grep.neogulcoder.domain.timevote.exception.code.TimeVoteErrorCode.*;

import grep.neogulcoder.domain.alram.type.AlarmType;
import grep.neogulcoder.domain.alram.type.DomainType;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
import grep.neogulcoder.global.exception.business.NotFoundException;
import grep.neogulcoder.global.provider.MessageProvidable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimeVoteMessageProvider implements MessageProvidable {

  private final StudyRepository studyRepository;

  @Override
  public boolean isSupport(AlarmType alarmType) {
    return alarmType == AlarmType.TIME_VOTE_REQUEST;
  }

  @Override
  public String provideMessage(DomainType domainType, Long domainId) {
    if (domainType != DomainType.TIME_VOTE) {
      throw new BusinessException(INVALID_DOMAIN_TYPE_FOR_TIME_VOTE_ALARM);
    }

    Study study = studyRepository.findById(domainId)
        .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND));

    return String.format("[%s] 스터디장이 모임 일정 조율을 위한 투표를 요청했습니다.", study.getName());
  }
}
