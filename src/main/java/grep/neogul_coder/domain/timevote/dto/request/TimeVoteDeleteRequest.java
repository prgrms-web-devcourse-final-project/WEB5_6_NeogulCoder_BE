package grep.neogul_coder.domain.timevote.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "스터디 모임 일정 조율 - 전체 지우기 요청 DTO")
public class TimeVoteDeleteRequest {

  @Schema(description = "스터디 멤버 ID", example = "12")
  private Long studyMemberId;

  private TimeVoteDeleteRequest() {}

  @Builder
  private TimeVoteDeleteRequest(Long studyMemberId) {
    this.studyMemberId = studyMemberId;
  }
}