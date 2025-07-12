package grep.neogul_coder.domain.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "스터디 게시글 댓글 요청 DTO")
public class CommentRequest {

  @Schema(description = "댓글 내용", example = "유익한 게시글이네욥!")
  @NotBlank
  private String content;
}
