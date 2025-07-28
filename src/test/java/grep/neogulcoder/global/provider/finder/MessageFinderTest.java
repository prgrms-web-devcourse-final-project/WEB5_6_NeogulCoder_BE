package grep.neogulcoder.global.provider.finder;

import grep.neogulcoder.domain.IntegrationTestSupport;
import grep.neogulcoder.domain.alram.type.AlarmType;
import grep.neogulcoder.domain.alram.type.DomainType;
import grep.neogulcoder.domain.recruitment.post.RecruitmentPost;
import grep.neogulcoder.domain.recruitment.post.repository.RecruitmentPostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class MessageFinderTest extends IntegrationTestSupport {

    @Autowired
    private RecruitmentPostRepository recruitmentPostRepository;

    @Autowired
    private MessageFinder messageFinder;

    @DisplayName("알림 타입이 모집글 댓글 타입일때 메세지를 찾습니다.")
    @Test
    void findMessage_WhenAlarmTypeIsRecruitmentPostComment() {
        //given
        RecruitmentPost recruitmentPost = createRecruitmentPost("모집글 제목", "내용");
        recruitmentPostRepository.save(recruitmentPost);

        //when
        String message = messageFinder.findMessage(
                AlarmType.RECRUITMENT_POST_COMMENT,
                DomainType.RECRUITMENT_POST,
                recruitmentPost.getId()
        );

        //then
        assertThat(message).isEqualTo("'모집글 제목' 모집글에 댓글이 달렸습니다.");
    }

    private RecruitmentPost createRecruitmentPost(String subject, String content) {
        return RecruitmentPost.builder()
                .subject(subject)
                .content(content)
                .build();
    }
}