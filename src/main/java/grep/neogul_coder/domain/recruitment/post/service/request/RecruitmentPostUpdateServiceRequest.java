package grep.neogul_coder.domain.recruitment.post.service.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RecruitmentPostUpdateServiceRequest {
    private String subject;
    private String content;
    private int recruitmentCount;
    private LocalDateTime expiredDateTime;

    @Builder
    private RecruitmentPostUpdateServiceRequest(String subject, String content, int recruitmentCount, LocalDateTime expiredDateTime) {
        this.subject = subject;
        this.content = content;
        this.recruitmentCount = recruitmentCount;
        this.expiredDateTime = expiredDateTime;
    }
}
