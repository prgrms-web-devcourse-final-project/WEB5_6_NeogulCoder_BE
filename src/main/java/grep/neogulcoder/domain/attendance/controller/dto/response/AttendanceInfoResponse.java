package grep.neogulcoder.domain.attendance.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class AttendanceInfoResponse {
    
    @Schema(description = "출석일 목록")
    private final List<AttendanceResponse> attendances;

    @Schema(description = "출석률", example = "50")
    private final int attendanceRate;

    @Builder
    private AttendanceInfoResponse(List<AttendanceResponse> attendances, int attendanceRate) {
        this.attendances = attendances;
        this.attendanceRate = attendanceRate;
    }

    public static AttendanceInfoResponse of(List<AttendanceResponse> responses, int attendanceRate) {
        return AttendanceInfoResponse.builder()
            .attendances(responses)
            .attendanceRate(attendanceRate)
            .build();
    }
}
