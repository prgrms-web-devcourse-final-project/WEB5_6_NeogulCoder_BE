package grep.neogul_coder.domain.timevote.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@Schema(description = "스터디 모임 일정 조율 - 단일 가능 시간 수정 요청 DTO")
public class TimeVoteUpdateRequest {

  @NotNull
  @Schema(description = "투표 ID", example = "100")
  private Long voteId;

  @NotNull
  @Schema(description = "변경할 시작 시간", example = "2025-07-16T15:00:00")
  private LocalDateTime startTime;

  @NotNull
  @Schema(description = "변경할 종료 시간", example = "2025-07-16T17:00:00")
  private LocalDateTime endTime;

}
