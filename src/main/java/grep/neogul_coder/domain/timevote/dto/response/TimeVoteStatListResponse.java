package grep.neogul_coder.domain.timevote.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(description = "스터디 모임 일정 조율 - 시간대별 통계 리스트 응답 DTO")
public class TimeVoteStatListResponse {

  @Schema(description = "시작일", example = "2025-07-15T00:00:00")
  private LocalDateTime startDate;

  @Schema(description = "종료일", example = "2025-07-22T00:00:00")
  private LocalDateTime endDate;

  @Schema(
      description = "투표 통계 리스트",
      example = "[" +
          "{\"timeSlot\": \"2025-07-16T10:00:00\", \"voteCount\": 3}," +
          "{\"timeSlot\": \"2025-07-16T11:00:00\", \"voteCount\": 2}" +
          "]"
  )
  private List<TimeVoteStatResponse> stats;
}
