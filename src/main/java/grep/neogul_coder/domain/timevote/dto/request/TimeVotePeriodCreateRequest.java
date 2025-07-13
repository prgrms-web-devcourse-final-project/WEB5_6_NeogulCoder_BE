package grep.neogul_coder.domain.timevote.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@Schema(description = "스터디 모임 일정 조율 - 팀장이 가능 시간 요청을 생성할 때 사용하는 요청 DTO")
public class TimeVotePeriodCreateRequest {

  @NotNull
  @Schema(description = "스터디 ID", example = "1")
  private Long studyId;

  @NotNull
  @Schema(description = "시작일", example = "2025-07-13T00:00:00")
  private LocalDateTime startDate;

  @NotNull
  @Schema(description = "종료일", example = "2025-07-20T23:59:59")
  private LocalDateTime endDate;
}
