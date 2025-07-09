package grep.neogul_coder.domain.calender.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "개인 캘린더 응답 DTO")
public class PersonalCalenderResponse {

    @Schema(description = "일정 ID", example = "1001")
    private Long scheduleId;

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "일정 제목", example = "면접 준비")
    private String title;

    @Schema(description = "일정 설명", example = "코테 대비 공부")
    private String description;

    @Schema(description = "시작 시간", example = "2025-07-10T09:00:00")
    private LocalDateTime startTime;

    @Schema(description = "종료 시간", example = "2025-07-10T10:00:00")
    private LocalDateTime endTime;

    @Builder
    private PersonalCalenderResponse(Long scheduleId, Long userId, String title, String description, LocalDateTime startTime, LocalDateTime endTime) {
        this.scheduleId = scheduleId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }



}
