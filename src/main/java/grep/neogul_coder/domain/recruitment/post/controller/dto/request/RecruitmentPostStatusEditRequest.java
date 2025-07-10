package grep.neogul_coder.domain.recruitment.post.controller.dto.request;

import grep.neogul_coder.domain.recruitment.RecruitmentPostStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class RecruitmentPostStatusEditRequest {

    @Schema(example = "IN_PROGRESS", description = "모집 글 상태")
    private RecruitmentPostStatus status;
}
