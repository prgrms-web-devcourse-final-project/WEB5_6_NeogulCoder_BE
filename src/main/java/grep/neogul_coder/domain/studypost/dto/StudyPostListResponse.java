package grep.neogul_coder.domain.studypost.dto;

import grep.neogul_coder.domain.studypost.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@Schema(description = "스터디 게시글 목록 응답 DTO")
public class StudyPostListResponse {

  @Schema(description = "게시글 ID", example = "12")
  private Long id;

  @Schema(description = "제목", example = "모든 국민은 직업선택의 자유를 가진다.")
  private String title;

  @Schema(description = "카테고리: NOTICE(공지), FREE(자유)", example = "NOTICE")
  private Category category;

  @Schema(description = "본문", example = "국회는 의원의 자격을 심사하며, 의원을 징계할 있다.")
  private String content;

  @Schema(description = "작성일", example = "2025-07-10T14:32:00")
  private LocalDateTime createdDate;

  @Schema(description = "댓글 수", example = "3")
  private int commentCount;
}
