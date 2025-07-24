package grep.neogulcoder.domain.recruitment.post.service.request;

import grep.neogulcoder.domain.recruitment.RecruitmentPostStatus;
import lombok.Getter;

@Getter
public class RecruitmentPostStatusUpdateServiceRequest {

    private RecruitmentPostStatus status;

    public RecruitmentPostStatusUpdateServiceRequest(RecruitmentPostStatus status) {
        this.status = status;
    }
}
