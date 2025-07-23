package grep.neogulcoder.domain.timevote.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "스터디 모임 일정 조율 - 사용자별 제출 여부 응답 DTO")
public class TimeVoteSubmissionStatusResponse {

  @Schema(description = "스터디 멤버 ID", example = "12")
  private Long studyMemberId;

  @Schema(description = "회원 nickname", example = "홍길동")
  private String nickname;

  @Schema(description = "회원 프로필 이미지", example = "profileImageUrl")
  private String profileImageUrl;

  @Schema(description = "제출 여부", example = "true")
  private boolean isSubmitted;

  @Builder
  public TimeVoteSubmissionStatusResponse(Long studyMemberId, String nickname, String profileImageUrl, boolean isSubmitted) {
    this.studyMemberId = studyMemberId;
    this.nickname = nickname;
    this.profileImageUrl = profileImageUrl;
    this.isSubmitted = isSubmitted;
  }
}
