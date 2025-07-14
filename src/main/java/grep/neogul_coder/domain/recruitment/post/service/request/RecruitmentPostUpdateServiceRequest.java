package grep.neogul_coder.domain.recruitment.post.service.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RecruitmentPostUpdateServiceRequest {
    private String subject;
    private String content;
    private int recruitmentCount;

    @Builder
    private RecruitmentPostUpdateServiceRequest(String subject, String content, int recruitmentCount) {
        this.subject = subject;
        this.content = content;
        this.recruitmentCount = recruitmentCount;
    }
}
