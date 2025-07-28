package grep.neogulcoder.domain.studypost.comment.event;

import grep.neogulcoder.domain.alram.type.AlarmType;
import grep.neogulcoder.domain.alram.type.DomainType;
import grep.neogulcoder.domain.recruitment.RecruitmentErrorCode;
import grep.neogulcoder.domain.studypost.StudyPost;
import grep.neogulcoder.domain.studypost.repository.StudyPostRepository;
import grep.neogulcoder.global.exception.business.NotFoundException;
import grep.neogulcoder.global.provider.MessageProvidable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StudyPostCommentMessageFinder implements MessageProvidable {

    private final StudyPostRepository postRepository;

    @Override
    public boolean isSupport(AlarmType alarmType) {
        return alarmType == AlarmType.STUDY_POST_COMMENT;
    }

    @Override
    public String provideMessage(DomainType domainType, Long domainId) {
        String subject = postRepository.findById(domainId)
                .map(StudyPost::getTitle)
                .orElseThrow(() -> new NotFoundException(RecruitmentErrorCode.NOT_FOUND));

        return String.format("'%s' 스터디 게시글에 댓글이 달렸습니다.", subject);
    }
}
