package grep.neogulcoder.domain.timevote.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "스터디 모임 일정 조율 - 가능 시간 제출 요청 DTO")
public class TimeVoteCreateRequest {

  @NotEmpty(message = "시간대를 1개 이상 선택해주세요.")
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
}
