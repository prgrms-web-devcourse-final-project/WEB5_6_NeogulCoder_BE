package grep.neogulcoder.domain.study.provider;

import grep.neogulcoder.domain.alram.type.AlarmType;
import grep.neogulcoder.domain.alram.type.DomainType;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.exception.code.StudyErrorCode;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
import grep.neogulcoder.global.exception.business.NotFoundException;
import grep.neogulcoder.global.provider.MessageProvidable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static grep.neogulcoder.domain.alram.exception.code.AlarmErrorCode.*;

@Component
@RequiredArgsConstructor
public class InviteMessageProvider implements MessageProvidable {

    private final StudyRepository studyRepository;

    @Override
    public boolean isSupport(AlarmType alarmType) {
        return alarmType == AlarmType.STUDY_INVITE;
    }

    @Override
    public String provideMessage(DomainType domainType, Long domainId) {
        if (domainType != DomainType.STUDY) {
            throw new BusinessException(INVALID_DOMAIN_TYPE_FOR_STUDY_INVITE_ALARM);
        }

        String studyName = studyRepository.findById(domainId)
            .map(Study::getName)
            .orElseThrow(() -> new NotFoundException(StudyErrorCode.STUDY_NOT_FOUND));

        return String.format("스터디: '%s' 에서 당신을 초대하고 싶어합니다.", studyName);
    }

}
