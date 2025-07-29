package grep.neogulcoder.domain.studyapplication.provider;

import grep.neogulcoder.domain.alram.type.AlarmType;
import grep.neogulcoder.domain.alram.type.DomainType;
import grep.neogulcoder.domain.recruitment.post.RecruitmentPost;
import grep.neogulcoder.domain.recruitment.post.repository.RecruitmentPostRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
import grep.neogulcoder.global.exception.business.NotFoundException;
import grep.neogulcoder.global.provider.MessageProvidable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static grep.neogulcoder.domain.alram.exception.code.AlarmErrorCode.INVALID_DOMAIN_TYPE_FOR_RECRUITMENT_ALARM;
import static grep.neogulcoder.domain.recruitment.RecruitmentErrorCode.NOT_FOUND;

@Component
@RequiredArgsConstructor
public class ApplicationMessageProvider implements MessageProvidable {

    private final RecruitmentPostRepository recruitmentPostRepository;

    @Override
    public boolean isSupport(AlarmType alarmType) {
        return alarmType == AlarmType.STUDY_APPLICATION;
    }

    @Override
    public String provideMessage(DomainType domainType, Long domainId) {
        if (domainType != DomainType.RECRUITMENT_POST) {
            throw new BusinessException(INVALID_DOMAIN_TYPE_FOR_RECRUITMENT_ALARM);
        }

        String recruitmentPostSubject = recruitmentPostRepository.findByIdAndActivatedTrue(domainId)
            .map(RecruitmentPost::getSubject)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));

        return String.format("모집글 '%s'에 새로운 신청이 들어왔습니다.", recruitmentPostSubject);
    }
}
