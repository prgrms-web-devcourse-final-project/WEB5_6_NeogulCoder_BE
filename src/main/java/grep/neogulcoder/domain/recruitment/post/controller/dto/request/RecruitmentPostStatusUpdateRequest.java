package grep.neogulcoder.domain.recruitment.post.controller.dto.request;

import grep.neogulcoder.domain.recruitment.RecruitmentPostStatus;
import grep.neogulcoder.domain.recruitment.post.service.request.RecruitmentPostStatusUpdateServiceRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RecruitmentPostStatusUpdateRequest {

    @Schema(example = "IN_PROGRESS, COMPLETE", description = "모집 글 상태")
    @NotNull
    private RecruitmentPostStatus status;

    public RecruitmentPostStatusUpdateServiceRequest toServiceRequest() {
        return new RecruitmentPostStatusUpdateServiceRequest(status);
    }

    private RecruitmentPostStatusUpdateRequest() {
    }
}
