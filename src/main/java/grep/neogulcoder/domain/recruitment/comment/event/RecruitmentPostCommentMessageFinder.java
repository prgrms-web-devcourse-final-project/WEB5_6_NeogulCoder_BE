package grep.neogulcoder.domain.recruitment.comment.event;

import grep.neogulcoder.domain.alram.type.AlarmType;
import grep.neogulcoder.domain.alram.type.DomainType;
import grep.neogulcoder.domain.recruitment.RecruitmentErrorCode;
import grep.neogulcoder.domain.recruitment.post.RecruitmentPost;
import grep.neogulcoder.domain.recruitment.post.repository.RecruitmentPostRepository;
import grep.neogulcoder.global.exception.business.NotFoundException;
import grep.neogulcoder.global.provider.MessageProvidable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RecruitmentPostCommentMessageFinder implements MessageProvidable {

    private final RecruitmentPostRepository postRepository;

    @Override
    public boolean isSupport(AlarmType alarmType) {
        return alarmType == AlarmType.RECRUITMENT_POST_COMMENT;
    }

    @Override
    public String provideMessage(DomainType domainType, Long domainId) {
        String subject = postRepository.findById(domainId)
                .map(RecruitmentPost::getSubject)
                .orElseThrow(() -> new NotFoundException(RecruitmentErrorCode.NOT_FOUND));

        return String.format("'%s' 모집글에 댓글이 달렸습니다.", subject);
    }
}
