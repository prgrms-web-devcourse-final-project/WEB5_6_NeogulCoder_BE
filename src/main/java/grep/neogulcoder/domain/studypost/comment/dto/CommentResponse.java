package grep.neogulcoder.domain.studypost.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@Schema(description = "스터디 게시글 댓글 응답 DTO")
public class CommentResponse {

  @Schema(description = "댓글 ID", example = "3")
  private Long id;

  @Schema(description = "회원 ID", example = "3")
  private Long userId;

  @Schema(description = "작성자 닉네임", example = "너굴코더")
  private String nickname;

  @Schema(description = "작성자 프로필 이미지 URL", example = "https://cdn.example.com/profile.jpg")
  private String profileImageUrl;

  @Schema(description = "댓글 내용", example = "정말 좋은 정보 감사합니다!")
  private String content;

  @Schema(description = "작성일", example = "2025-07-10T14:45:00")
  private LocalDateTime createdAt;
}
