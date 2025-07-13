package grep.neogul_coder.domain.timevote.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@Schema(description = "스터디 모임 일정 조율 - 단일 시간 삭제 요청 DTO")
public class TimeVoteDeleteRequest {

  @Schema(description = "투표 기간 ID", example = "5")
  private Long periodId;

  @Schema(description = "스터디 멤버 ID", example = "12")
  private Long studyMemberId;

  @Schema(description = "삭제할 시작 시간", example = "2025-07-16T10:00:00")
  private LocalDateTime startTime;

  @Schema(description = "삭제할 종료 시간", example = "2025-07-16T13:00:00")
  private LocalDateTime endTime;
}