package grep.neogulcoder.domain.timevote.dto.response;

import grep.neogulcoder.domain.timevote.TimeVotePeriod;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "스터디 모임 일정 조율 - 팀장이 요청한 가능 시간 투표 기간 정보를 응답할 때 사용하는 DTO")
public class TimeVotePeriodResponse {

  @Schema(description = "스터디 ID", example = "6")
  private Long studyId;

  @Schema(description = "시작일", example = "2025-07-25T00:00:00")
  private LocalDateTime startDate;

  @Schema(description = "종료일", example = "2025-07-30T23:59:59")
  private LocalDateTime endDate;

  @Builder
  private TimeVotePeriodResponse(Long studyId, LocalDateTime startDate, LocalDateTime endDate) {
    this.studyId = studyId;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public static TimeVotePeriodResponse from(TimeVotePeriod timeVotePeriod) {
    return TimeVotePeriodResponse.builder()
        .studyId(timeVotePeriod.getStudyId())
        .startDate(timeVotePeriod.getStartDate())
        .endDate(timeVotePeriod.getEndDate())
        .build();
  }
}

