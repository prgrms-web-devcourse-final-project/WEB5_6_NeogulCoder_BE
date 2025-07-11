package grep.neogul_coder.domain.attendance.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AttendanceResponse {

    @Schema(description = "출석일", example = "2025-07-10")
    private LocalDate attendanceDate;

    @Schema(description = "출석 여부", example = "true")
    private boolean isPresent;
}
