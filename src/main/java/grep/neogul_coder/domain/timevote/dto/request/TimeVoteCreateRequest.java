package grep.neogul_coder.domain.timevote.dto.request;

import grep.neogul_coder.domain.timevote.entity.TimeVote;
import grep.neogul_coder.domain.timevote.entity.TimeVotePeriod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "스터디 모임 일정 조율 - 가능 시간 제출 요청 DTO")
public class TimeVoteCreateRequest {

  @NotEmpty
  @Schema(
      description = "시간대 리스트",
      example = "[\"2025-07-25T10:00:00\", \"2025-07-26T11:00:00\"]"
  )
  private List<LocalDateTime> timeSlots;

  private TimeVoteCreateRequest() {}

  @Builder
  private TimeVoteCreateRequest(List<LocalDateTime> timeSlots) {
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
