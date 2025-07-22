package grep.neogul_coder.domain.timevote.dto.response;

import grep.neogul_coder.domain.timevote.dto.request.TimeVoteCreateRequest;
import grep.neogul_coder.domain.timevote.entity.TimeVote;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "스터디 모임 일정 조율 - 제출된 가능 시간 정보에 대한 응답 DTO")
public class TimeVoteResponse {

  @Schema(description = "스터디 멤버 ID", example = "12")
  private Long studyMemberId;

  @Schema(
      description = "시간대 리스트",
      example = "[\"2025-07-16T10:00:00\", \"2025-07-16T11:00:00\", \"2025-07-16T13:00:00\", \"2025-07-18T11:00:00\"]"
  )
  private List<LocalDateTime> timeSlots;
}
