package grep.neogulcoder.domain.calendar.controller.dto.requset;

import grep.neogulcoder.domain.calendar.entity.Calendar;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;


@Schema(description = "개인 캘린더 요청 DTO")
@Getter
public class PersonalCalendarRequest {

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @NotBlank(message = "일정 제목은 필수입니다.")
    @Schema(description = "일정 제목", example = "면접 준비")
    private String title;

    @Schema(description = "일정 설명", example = "코테 대비 공부")
    private String description;

    @NotNull(message = "시작 기간은 필수입니다.")
    @Schema(description = "시작 기간", example = "2025-07-10T09:00:00")
    private LocalDateTime startTime;

    @NotNull(message = "종료 기간은 필수입니다.")
    @Schema(description = "종료 기간", example = "2025-07-10T10:00:00")
    private LocalDateTime endTime;

    public Calendar toCalendar() {
        return Calendar.builder()
            .title(title)
            .content(description)
            .scheduledStart(startTime)
            .scheduledEnd(endTime)
            .build();
    }

    protected PersonalCalendarRequest() {
    }
}
