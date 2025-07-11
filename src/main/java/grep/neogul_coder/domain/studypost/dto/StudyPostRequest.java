package grep.neogul_coder.domain.studypost.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "스터디 게시글 저장/수정 요청 DTO")
public class StudyPostRequest {

  @Schema(description = "제목", example = "스터디 공지")
  @NotBlank
  private String title;

  @Schema(description = "카테고리: NOTICE(공지), FREE(자유)", example = "NOTICE")
  @NotBlank
  private String category;

  @Schema(description = "내용", example = "오늘은 각자 공부한 내용에 대해 발표가 있는 날 입니다!")
  @NotBlank
  private String content;
}
