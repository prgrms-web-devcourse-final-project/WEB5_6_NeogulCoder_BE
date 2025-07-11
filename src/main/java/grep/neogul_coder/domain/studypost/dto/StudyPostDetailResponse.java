package grep.neogul_coder.domain.studypost.dto;

import grep.neogul_coder.domain.comment.dto.CommentResponse;
import grep.neogul_coder.domain.studypost.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(description = "스터디 게시글 상세 응답 DTO")
public class StudyPostDetailResponse {

  @Schema(description = "게시글 ID", example = "10")
  private Long id;

  @Schema(description = "제목", example = "모든 국민은 직업선택의 자유를 가진다.")
  private String title;

  @Schema(description = "카테고리: NOTICE(공지), FREE(자유)", example = "NOTICE")
  private Category category;

  @Schema(description = "본문", example = "국회는 의원의 자격을 심사하며, 의원을 징계할 있다.")
  private String content;

  @Schema(description = "작성일", example = "2025-07-10T14:00:00")
  private LocalDateTime createdDate;

  @Schema(description = "작성자 닉네임", example = "너굴코더")
  private String nickname;

  @Schema(description = "작성자 프로필 이미지 URL", example = "https://cdn.example.com/profile.jpg")
  private String profileImageUrl;

  @Schema(description = "댓글 수", example = "3")
  private int commentCount;

  @Schema(description = "댓글 목록")
  private List<CommentResponse> comments;
}
