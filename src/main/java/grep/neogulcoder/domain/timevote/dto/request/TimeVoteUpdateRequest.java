package grep.neogulcoder.domain.timevote.dto.request;

import grep.neogulcoder.domain.timevote.TimeVote;
import grep.neogulcoder.domain.timevote.TimeVotePeriod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "스터디 모임 일정 조율 - 가능 시간 수정 요청 DTO")
public class TimeVoteUpdateRequest {

  @NotEmpty
  @Schema(
      description = "시간대 리스트",
      example = "[\"2025-07-27T10:00:00\", \"2025-07-28T11:00:00\"]"
  )
  private List<LocalDateTime> timeSlots;

  private TimeVoteUpdateRequest() {}

  @Builder
  private TimeVoteUpdateRequest( List<LocalDateTime> timeSlots) {
    this.timeSlots = timeSlots;
  }

  public List<TimeVote> toEntities(TimeVotePeriod period, Long studyMemberId) {
    return timeSlots.stream()
        .map(slot -> TimeVote.builder()
            .period(period)
            .studyMemberId(studyMemberId)
            .timeSlot(slot)
            .build())
        .collect(Collectors.toList());
  }
}
