package grep.neogul_coder.domain.timevote.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@Schema(description = "스터디 모임 일정 조율 - 시간대별 통계 응답 DTO")
public class TimeVoteStatResponse {

  @Schema(description = "시작 시간", example = "2025-07-16T10:00:00")
  private LocalDateTime startTime;

  @Schema(description = "종료 시간", example = "2025-07-16T11:00:00")
  private LocalDateTime endTime;

  @Schema(description = "투표 수", example = "3")
  private Long voteCount;
}
