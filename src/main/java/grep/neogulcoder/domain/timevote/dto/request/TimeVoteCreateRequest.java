package grep.neogulcoder.domain.timevote.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

import static grep.neogulcoder.domain.timevote.provider.TimeSlotBitmaskConverter.expand;

@Getter
@Schema(description = "스터디 모임 일정 조율 - 가능 시간 제출 요청 DTO")
public class TimeVoteCreateRequest {

  @NotEmpty(message = "시간대는 최소 한 개 이상의 일자 정보를 포함해야 합니다.")
  @Schema(
      description = "일자별 시간 bitmask 리스트 (LSB=0시, 1시간 단위)",
      example = "[" +
          "{\"date\": \"2025-08-11\", \"timeMask\": 10}," +
          "{\"date\": \"2025-08-12\", \"timeMask\": 384}" +
          "]"
  )
  private List<TimeVoteDateMaskRequest> timeMasks;

  private TimeVoteCreateRequest() {}

  @Builder
  private TimeVoteCreateRequest(List<TimeVoteDateMaskRequest> timeMasks) {
    this.timeMasks = timeMasks;
  }

  public List<LocalDateTime> toTimeSlots() {
    return expand(timeMasks);
  }
}
