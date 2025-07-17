package grep.neogul_coder.domain.recruitment.comment.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class RecruitmentCommentUpdateRequest {

    @Schema(example = "저도 참여 할래요!", description = "모집글 내용")
    private String content;
}
