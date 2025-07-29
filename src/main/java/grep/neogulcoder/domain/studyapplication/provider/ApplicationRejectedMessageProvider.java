package grep.neogulcoder.domain.studyapplication.provider;

import grep.neogulcoder.domain.alram.type.AlarmType;
import grep.neogulcoder.domain.alram.type.DomainType;
import grep.neogulcoder.domain.recruitment.post.RecruitmentPost;
import grep.neogulcoder.domain.recruitment.post.repository.RecruitmentPostRepository;
import grep.neogulcoder.domain.studyapplication.StudyApplication;
import grep.neogulcoder.domain.studyapplication.repository.ApplicationRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
import grep.neogulcoder.global.exception.business.NotFoundException;
import grep.neogulcoder.global.provider.MessageProvidable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static grep.neogulcoder.domain.alram.exception.code.AlarmErrorCode.*;
import static grep.neogulcoder.domain.recruitment.RecruitmentErrorCode.*;
import static grep.neogulcoder.domain.studyapplication.exception.code.ApplicationErrorCode.*;

@Component
@RequiredArgsConstructor
public class ApplicationRejectedMessageProvider implements MessageProvidable {

    private final ApplicationRepository applicationRepository;
    private final RecruitmentPostRepository recruitmentPostRepository;

    @Override
    public boolean isSupport(AlarmType alarmType) {
        return alarmType == AlarmType.STUDY_APPLICATION_REJECTED;
    }

    @Override
    public String provideMessage(DomainType domainType, Long domainId) {
        if (domainType != DomainType.STUDY_APPLICATION) {
            throw new BusinessException(INVALID_DOMAIN_TYPE_FOR_APPLICATION_REJECTED_ALARM);
        }

        StudyApplication application = applicationRepository.findByIdAndActivatedTrue(domainId)
            .orElseThrow(() -> new NotFoundException(APPLICATION_NOT_FOUND));

        String subject = recruitmentPostRepository.findById(application.getRecruitmentPostId())
            .map(RecruitmentPost::getSubject)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));

        return String.format("당신이 지원한 '%s' 스터디가 거절되었습니다.", subject);
    }
}
