package grep.neogul_coder.domain.attendance.controller.dto.response;

import grep.neogul_coder.domain.attendance.Attendance;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AttendanceResponse {

    @Schema(description = "스터디 Id", example = "1")
    private Long studyId;

    @Schema(description = "유저 Id", example = "2")
    private Long userId;

    @Schema(description = "출석일", example = "2025-07-10")
    private LocalDate attendanceDate;

    @Builder
    private AttendanceResponse(Long studyId, Long userId, LocalDate attendanceDate) {
        this.studyId = studyId;
        this.userId = userId;
        this.attendanceDate = attendanceDate;
    }

    public static AttendanceResponse from(Attendance attendance) {
        return AttendanceResponse.builder()
            .studyId(attendance.getStudyId())
            .userId(attendance.getUserId())
            .attendanceDate(attendance.getAttendanceDate().toLocalDate())
            .build();
    }
}
