package grep.neogul_coder.domain.attendance.controller;

import grep.neogul_coder.domain.attendance.controller.dto.response.AttendanceResponse;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Attendance", description = "출석 API")
public interface AttendanceSpecification {

    @Operation(summary = "출석 조회", description = "일주일 단위로 출석을 조회합니다.")
    ApiResponse<List<AttendanceResponse>> getAttendances();
}
