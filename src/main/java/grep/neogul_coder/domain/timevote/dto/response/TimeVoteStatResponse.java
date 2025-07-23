package grep.neogul_coder.domain.timevote.dto.response;

import grep.neogul_coder.domain.timevote.entity.TimeVoteStat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "스터디 모임 일정 조율 - 시간대별 통계 응답 DTO")
public class TimeVoteStatResponse {

  @Schema(description = "시간대", example = "2025-07-16T10:00:00")
  private LocalDateTime timeSlot;

  @Schema(description = "해당 시간대의 투표 수", example = "3")
  private Long voteCount;

  @Builder
  private TimeVoteStatResponse(LocalDateTime timeSlot, Long voteCount) {
    this.timeSlot = timeSlot;
    this.voteCount = voteCount;
  }

  public static TimeVoteStatResponse from(TimeVoteStat stat) {
    return TimeVoteStatResponse.builder()
        .timeSlot(stat.getTimeSlot())
        .voteCount(stat.getVoteCount())
        .build();
  }
}
