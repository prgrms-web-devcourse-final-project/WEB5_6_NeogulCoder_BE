package grep.neogul_coder.domain.timevote.dto.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(description = "스터디 모임 일정 조율 - 여러 시간 삭제 요청 DTO")
public class TimeVoteBulkDeleteRequest {

  @Schema(description = "스터디 멤버 ID", example = "12")
  private Long studyMemberId;

  @Schema(description = "투표 기간 ID", example = "5")
  private Long periodId;

  @Valid
  @NotEmpty
  @ArraySchema(arraySchema = @Schema(description = "삭제할 시간대 목록",
          example = "[{\"startTime\":\"2025-07-16T10:00:00\",\"endTime\":\"2025-07-16T12:00:00\"}," +
              "{\"startTime\":\"2025-07-17T13:00:00\",\"endTime\":\"2025-07-17T15:00:00\"}]"))
  private List<TimeRange> timeRanges;

  @Getter
  @Schema(description = "시간 범위")
  public static class TimeRange {
    @Schema(description = "시작 시간", example = "2025-07-16T10:00:00")
    private LocalDateTime startTime;

    @Schema(description = "종료 시간", example = "2025-07-16T13:00:00")
    private LocalDateTime endTime;
  }
}
