package grep.neogul_coder.domain.timevote.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@Schema(description = "스터디 모임 일정 조율 - 제출된 가능 시간 정보에 대한 응답 DTO")
public class TimeVoteResponse {

  @Schema(description = "투표 ID", example = "100")
  private Long voteId;

  @Schema(description = "기간 ID", example = "5")
  private Long periodId;

  @Schema(description = "스터디 멤버 ID", example = "12")
  private Long studyMemberId;

  @Schema(description = "시작 시간", example = "2025-07-16T10:00:00")
  private LocalDateTime startTime;

  @Schema(description = "종료 시간", example = "2025-07-16T13:00:00")
  private LocalDateTime endTime;
}
