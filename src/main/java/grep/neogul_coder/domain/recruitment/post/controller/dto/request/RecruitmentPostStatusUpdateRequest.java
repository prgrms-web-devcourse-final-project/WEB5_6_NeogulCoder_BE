package grep.neogul_coder.domain.recruitment.post.controller.dto.request;

import grep.neogul_coder.domain.recruitment.RecruitmentPostStatus;
import grep.neogul_coder.domain.recruitment.post.service.request.RecruitmentPostStatusUpdateServiceRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RecruitmentPostStatusUpdateRequest {

    @Schema(example = "IN_PROGRESS", description = "모집 글 상태")
    @NotNull
    private RecruitmentPostStatus status;

    public RecruitmentPostStatusUpdateServiceRequest toServiceRequest() {
        return new RecruitmentPostStatusUpdateServiceRequest(status);
    }
}
