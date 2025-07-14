package grep.neogul_coder.domain.calender.controller.dto.requset;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@Schema(description = "팀 캘린더 요청 DTO")
public class TeamCalenderRequest {

    @Schema(description = "팀 ID", example = "101")
    private Long teamId;

    @Schema(description = "일정 제목", example = "주간 회의")
    private String title;

    @Schema(description = "일정 설명", example = "스터디 진행 사항 공유")
    private String description;

    @Schema(description = "시작 시간", example = "2025-07-12T14:00:00")
    private LocalDateTime startTime;

    @Schema(description = "종료 시간", example = "2025-07-12T15:00:00")
    private LocalDateTime endTime;

}
