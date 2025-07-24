package grep.neogulcoder.domain.timevote.dto.response;

import grep.neogulcoder.domain.timevote.entity.TimeVoteStat;
import grep.neogulcoder.domain.timevote.entity.TimeVotePeriod;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Comparator;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Schema(description = "스터디 모임 일정 조율 - 시간대별 통계 응답 DTO")
public class TimeVoteStatResponse {

  @Schema(description = "시작일", example = "2025-07-15")
  private LocalDateTime startDate;

  @Schema(description = "종료일", example = "2025-07-22")
  private LocalDateTime endDate;

  @Schema(
      description = "투표 통계 리스트",
      example = "[" +
          "{\"timeSlot\": \"2025-07-16T10:00:00\", \"voteCount\": 3}," +
          "{\"timeSlot\": \"2025-07-16T11:00:00\", \"voteCount\": 2}" +
          "]"
  )
  private List<TimeSlotStat> stats;

  @Builder
  private TimeVoteStatResponse(LocalDateTime startDate, LocalDateTime endDate, List<TimeSlotStat> stats) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.stats = stats;
  }

  @Getter
  @Schema(description = "개별 시간대별 통계 DTO")
  public static class TimeSlotStat {

    @Schema(description = "시간대", example = "2025-07-16T10:00:00")
    private LocalDateTime timeSlot;

    @Schema(description = "해당 시간대의 투표 수", example = "3")
    private Long voteCount;

    @Builder
    public TimeSlotStat(LocalDateTime timeSlot, Long voteCount) {
      this.timeSlot = timeSlot;
      this.voteCount = voteCount;
    }
  }

  public static TimeVoteStatResponse from(TimeVotePeriod period, List<TimeVoteStat> stats) {
    return TimeVoteStatResponse.builder()
        .startDate(period.getStartDate())
        .endDate(period.getEndDate())
        .stats(stats.stream()
            .sorted(Comparator.comparing(TimeVoteStat::getTimeSlot))
            .map(s -> TimeSlotStat.builder()
                .timeSlot(s.getTimeSlot())
                .voteCount(s.getVoteCount())
                .build())
            .collect(Collectors.toList()))
        .build();
  }
}
