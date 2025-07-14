package grep.neogul_coder.domain.timevote.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@Schema(description = "스터디 모임 일정 조율 - 단일 가능 시간 제출 요청 DTO")
public class TimeVoteCreateRequest {

  @NotNull
  @Schema(description = "기간 ID", example = "5")
  private Long periodId;

  @NotNull
  @Schema(description = "스터디 멤버 ID", example = "12")
  private Long studyMemberId;

  @NotNull
  @Schema(description = "시작 시간", example = "2025-07-16T10:00:00")
  private LocalDateTime startTime;

  @NotNull
  @Schema(description = "종료 시간", example = "2025-07-16T13:00:00")
  private LocalDateTime endTime;
}
