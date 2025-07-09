package grep.neogul_coder.domain.calender.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;


@Schema(description = "개인 캘린더 요청 DTO")
@Getter
public class PersonalCalenderRequest {

    @Schema(description = "일정 제목", example = "면접 준비")
    private String title;

    @Schema(description = "일정 설명", example = "코테 대비 공부")
    private String description;

    @Schema(description = "시작 시간", example = "2025-07-10T09:00:00")
    private LocalDateTime startTime;

    @Schema(description = "종료 시간", example = "2025-07-10T10:00:00")
    private LocalDateTime endTime;

}
