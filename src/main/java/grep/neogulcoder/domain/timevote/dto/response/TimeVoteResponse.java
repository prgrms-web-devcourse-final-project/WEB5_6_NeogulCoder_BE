package grep.neogulcoder.domain.timevote.dto.response;

import static grep.neogulcoder.domain.timevote.provider.TimeSlotBitmaskConverter.compress;

import grep.neogulcoder.domain.timevote.TimeVote;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "스터디 모임 일정 조율 - 제출된 가능 시간 정보에 대한 응답 DTO")
public class TimeVoteResponse {

  @Schema(description = "스터디 멤버 ID", example = "12")
  private Long studyMemberId;

  @Schema(
      description = "사용자가 제출한 개별 시간대 리스트",
      example = "[\"2025-07-26T10:00:00\", \"2025-07-26T11:00:00\"]"
  )
  private List<LocalDateTime> timeSlots;

  @Schema(
      description = "일자별 시간 비트마스크 (LSB=0시, 1시간 단위)",
      example = "{\"2025-07-26\": 3, \"2025-07-27\": 384}"
  )
  private Map<LocalDate, Long> timeMasks;

  @Builder
  private TimeVoteResponse(Long studyMemberId, List<LocalDateTime> timeSlots, Map<LocalDate, Long> timeMasks) {
    this.studyMemberId = studyMemberId;
    this.timeSlots = timeSlots;
    this.timeMasks = timeMasks;
  }

  public static TimeVoteResponse from(Long studyMemberId, List<TimeVote> votes) {
    List<LocalDateTime> timeSlots = votes.stream()
        .map(TimeVote::getTimeSlot)
        .collect(Collectors.toList());

    Map<LocalDate, Long> timeMasks = compress(votes);

    return TimeVoteResponse.builder()
        .studyMemberId(studyMemberId)
        .timeSlots(timeSlots)
        .timeMasks(timeMasks)
        .build();
  }
}
