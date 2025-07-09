package grep.neogul_coder.domain.calender.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "팀 캘린더 응답 DTO")
public class TeamCalenderResponse {

    @Schema(description = "일정 ID", example = "2001")
    private Long scheduleId;

    @Schema(description = "팀 ID", example = "101")
    private Long teamId;

    @Schema(description = "일정 제목", example = "스터디A")
    private String title;

    @Schema(description = "일정 설명", example = "기획 회의")
    private String description;

    @Schema(description = "시작 시간", example = "2025-07-12T14:00:00")
    private LocalDateTime startTime;

    @Schema(description = "종료 시간", example = "2025-07-12T15:00:00")
    private LocalDateTime endTime;

    public TeamCalenderResponse(Long scheduleId, Long teamId, String title, String description, LocalDateTime startTime, LocalDateTime endTime) {
        this.scheduleId = scheduleId;
        this.teamId = teamId;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // (전체 조회용 생성자)
    public TeamCalenderResponse(Long scheduleId, Long teamId, String title, LocalDateTime startTime, LocalDateTime endTime) {
        this.scheduleId = scheduleId;
        this.teamId = teamId;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
