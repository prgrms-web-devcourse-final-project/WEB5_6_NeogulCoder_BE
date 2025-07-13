package grep.neogul_coder.domain.recruitment.post.service.request;

import grep.neogul_coder.domain.recruitment.RecruitmentPostStatus;
import lombok.Getter;

@Getter
public class RecruitmentPostStatusUpdateServiceRequest {

    private RecruitmentPostStatus status;

    public RecruitmentPostStatusUpdateServiceRequest(RecruitmentPostStatus status) {
        this.status = status;
    }
}
