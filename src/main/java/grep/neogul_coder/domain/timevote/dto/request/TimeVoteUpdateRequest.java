package grep.neogul_coder.domain.timevote.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "스터디 모임 일정 조율 - 가능 시간 수정 요청 DTO")
public class TimeVoteUpdateRequest {

  @NotNull
  @Schema(description = "스터디 멤버 ID", example = "12")
  private Long studyMemberId;

  @NotEmpty
  @Schema(description = "시간대 리스트", example = "[2025-07-15T10:00:00, 2025-07-15T11:00:00]")
  private List<LocalDateTime> timeSlots;

  private TimeVoteUpdateRequest() {}

  @Builder
  private TimeVoteUpdateRequest(Long studyMemberId, List<LocalDateTime> timeSlots) {
    this.studyMemberId = studyMemberId;
    this.timeSlots = timeSlots;
  }
}
