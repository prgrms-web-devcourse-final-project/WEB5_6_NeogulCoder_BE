package grep.neogulcoder.domain.timevote.dto.response;

import grep.neogulcoder.domain.timevote.entity.TimeVote;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "스터디 모임 일정 조율 - 제출된 가능 시간 정보에 대한 응답 DTO")
public class TimeVoteResponse {

  @Schema(description = "스터디 멤버 ID", example = "12")
  private Long studyMemberId;

  @Schema(
      description = "시간대 리스트",
      example = "[\"2025-07-26T10:00:00\", \"2025-07-26T11:00:00\", \"2025-07-26T13:00:00\", \"2025-07-28T11:00:00\"]"
  )
  private List<LocalDateTime> timeSlots;

  @Builder
  private TimeVoteResponse(Long studyMemberId, List<LocalDateTime> timeSlots) {
    this.studyMemberId = studyMemberId;
    this.timeSlots = timeSlots;
  }

  public static TimeVoteResponse from(Long studyMemberId, List<TimeVote> votes) {
    List<LocalDateTime> timeSlots = votes.stream()
        .map(TimeVote::getTimeSlot)
        .collect(Collectors.toList());

    return TimeVoteResponse.builder()
        .studyMemberId(studyMemberId)
        .timeSlots(timeSlots)
        .build();
  }
}
