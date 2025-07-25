package grep.neogulcoder.domain.timevote.dto.response;

import grep.neogulcoder.domain.timevote.entity.TimeVotePeriod;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "스터디 모임 일정 조율 - 팀장이 요청한 가능 시간 투표 기간 정보를 응답할 때 사용하는 DTO")
public class TimeVotePeriodResponse {

  @Schema(description = "스터디 ID", example = "6")
  private Long studyId;

  @Schema(description = "시작일", example = "2025-07-25T00:00:00")
  private LocalDateTime startDate;

  @Schema(description = "종료일", example = "2025-07-30T23:59:59")
  private LocalDateTime endDate;

  @Schema(description = "알림이 전송된 팀원 멤버 ID 목록", example = "[11, 12, 13]")
  private List<Long> notifiedMemberIds;

  @Builder
  private TimeVotePeriodResponse(Long studyId, LocalDateTime startDate, LocalDateTime endDate, List<Long> notifiedMemberIds) {
    this.studyId = studyId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.notifiedMemberIds = notifiedMemberIds;
  }

  public static TimeVotePeriodResponse from(TimeVotePeriod timeVotePeriod, List<Long> notifiedMemberIds) {
    return TimeVotePeriodResponse.builder()
        .studyId(timeVotePeriod.getStudyId())
        .startDate(timeVotePeriod.getStartDate())
        .endDate(timeVotePeriod.getEndDate())
        .notifiedMemberIds(notifiedMemberIds)
        .build();
  }
}

