package grep.neogul_coder.domain.studypost.comment.dto;

import grep.neogul_coder.domain.studypost.comment.StudyPostComment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "스터디 게시글 댓글 생성 요청 DTO")
public class StudyPostCommentSaveRequest {

    @Schema(description = "댓글 내용", example = "유익한 게시글이네욥!")
    @NotBlank
    private String content;

    private StudyPostCommentSaveRequest() {}

    public StudyPostComment toEntity(long postId, long userId) {
        return StudyPostComment.builder()
                .postId(postId)
                .userId(userId)
                .content(this.content)
                .build();
    }
}
