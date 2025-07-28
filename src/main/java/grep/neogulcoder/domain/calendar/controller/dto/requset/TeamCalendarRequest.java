package grep.neogulcoder.domain.calendar.controller.dto.requset;

import grep.neogulcoder.domain.calendar.entity.Calendar;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@Schema(description = "팀 캘린더 요청 DTO")
public class TeamCalendarRequest {

    @Schema(description = "스터디 ID", example = "101")
    private Long studyId;

    @NotBlank(message = "일정 제목은 필수입니다.")
    @Schema(description = "일정 제목", example = "주간 회의")
    private String title;

    @Schema(description = "일정 설명", example = "스터디 진행 사항 공유")
    private String description;

    @NotNull(message = "시작 기간은 필수입니다.")
    @Schema(description = "시작 기간", example = "2025-07-12T14:00:00")
    private LocalDateTime startTime;

    @NotNull(message = "종료 기간은 필수입니다.")
    @Schema(description = "종료 기간", example = "2025-07-12T15:00:00")
    private LocalDateTime endTime;

    public Calendar toCalendar() {
        return Calendar.builder()
            .title(title)
            .content(description)
            .scheduledStart(startTime)
            .scheduledEnd(endTime)
            .build();
    }

    protected TeamCalendarRequest() {
    }
}
