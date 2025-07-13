package grep.neogul_coder.domain.timevote.dto.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(description = "스터디 모임 일정 조율 - 여러 가능 시간 제출 요청 DTO")
public class TimeVoteBulkCreateRequest {

  @NotNull
  @Schema(description = "기간 ID", example = "5")
  private Long periodId;

  @NotNull
  @Schema(description = "스터디 멤버 ID", example = "12")
  private Long studyMemberId;

  @Valid
  @NotEmpty
  @ArraySchema(arraySchema = @Schema(description = "제출할 가능 시간 목록",
      example = "[{\"startTime\":\"2025-07-16T10:00:00\",\"endTime\":\"2025-07-16T12:00:00\"}, {\"startTime\":\"2025-07-17T13:00:00\",\"endTime\":\"2025-07-17T15:00:00\"}]"))
  private List<AvailableTimeDto> availableTimes;

  @Getter
  @Schema(description = "가능 시간 블록 DTO")
  public static class AvailableTimeDto {

    @NotNull
    @Schema(description = "가능 시작 시간", example = "2025-07-16T10:00:00")
    private LocalDateTime startTime;

    @NotNull
    @Schema(description = "가능 종료 시간", example = "2025-07-16T12:00:00")
    private LocalDateTime endTime;
  }
}
