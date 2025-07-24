package grep.neogulcoder.domain.attendance.controller;

import grep.neogulcoder.domain.attendance.controller.dto.response.AttendanceInfoResponse;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Attendance", description = "출석 API")
public interface AttendanceSpecification {

    @Operation(summary = "출석 조회", description = "일주일 단위로 출석을 조회합니다.")
    ApiResponse<AttendanceInfoResponse> getAttendances(Long studyId, Principal userDetails);

    @Operation(summary = "출석 체크", description = "스터디에 출석을 합니다.")
    ApiResponse<Long> createAttendance(Long studyId, Principal userDetails);
}
