package grep.neogul_coder.domain.timevote.dto.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(description = "스터디 모임 일정 조율 - 여러 가능 시간 수정 요청 DTO")
public class TimeVoteBulkUpdateRequest {

  @Schema(description = "투표 기간 ID", example = "5")
  private Long periodId;

  @Schema(description = "스터디 멤버 ID", example = "12")
  private Long studyMemberId;

  @Valid
  @NotEmpty
  @ArraySchema(arraySchema = @Schema(description = "수정할 가능 시간 목록",
      example = "[{\"voteId\":100,\"startTime\":\"2025-07-16T10:00:00\",\"endTime\":\"2025-07-16T12:00:00\"}, {\"voteId\":101,\"startTime\":\"2025-07-17T13:00:00\",\"endTime\":\"2025-07-17T15:00:00\"}]"))
  private List<VoteUpdateBlock> voteBlocks;

  @Getter
  @Schema(description = "수정할 시간 블록")
  public static class VoteUpdateBlock {
    @NotNull
    @Schema(description = "투표 ID", example = "100")
    private Long voteId;

    @NotNull
    @Schema(description = "시작 시간", example = "2025-07-16T16:00:00")
    private LocalDateTime startTime;

    @NotNull
    @Schema(description = "종료 시간", example = "2025-07-16T19:00:00")
    private LocalDateTime endTime;
  }
}

