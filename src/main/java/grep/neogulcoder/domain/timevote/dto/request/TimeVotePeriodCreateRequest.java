package grep.neogulcoder.domain.timevote.dto.request;

import grep.neogulcoder.domain.timevote.entity.TimeVotePeriod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "스터디 모임 일정 조율 - 팀장이 가능 시간 요청을 생성할 때 사용하는 요청 DTO")
public class TimeVotePeriodCreateRequest {

  @NotNull
  @Schema(description = "시작일", example = "2025-07-25T00:00:00")
  private LocalDateTime startDate;

  @NotNull
  @Schema(description = "종료일", example = "2025-07-30T23:59:59")
  private LocalDateTime endDate;

  private TimeVotePeriodCreateRequest() {}

  @Builder
  private TimeVotePeriodCreateRequest(LocalDateTime startDate, LocalDateTime endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public TimeVotePeriod toEntity(Long studyId, LocalDateTime adjustedEndDate) {
    return TimeVotePeriod.builder()
        .startDate(this.startDate)
        .endDate(adjustedEndDate)
        .studyId(studyId)
        .build();
  }

  public static TimeVotePeriodCreateRequest of(LocalDateTime startDate, LocalDateTime endDate) {
    return new TimeVotePeriodCreateRequest(startDate, endDate);
  }
}
