package grep.neogulcoder.domain.studypost.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class StudyCommentUpdateRequest {

    @Schema(description = "댓글 내용", example = "유익한 게시글이네욥!")
    @NotBlank
    private String content;

    private StudyCommentUpdateRequest() {}
}
