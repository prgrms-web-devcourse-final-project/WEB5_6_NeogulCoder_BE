package grep.neogulcoder.domain.attendance.controller;

import grep.neogulcoder.domain.attendance.controller.dto.response.AttendanceInfoResponse;
import grep.neogulcoder.domain.attendance.service.AttendanceService;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/studies/{studyId}/attendances")
@RequiredArgsConstructor
@RestController
public class AttendanceController implements AttendanceSpecification {

    private final AttendanceService attendanceService;

    @GetMapping
    public ResponseEntity<ApiResponse<AttendanceInfoResponse>> getAttendances(@PathVariable("studyId") Long studyId,
                                                                              @AuthenticationPrincipal Principal userDetails) {
        AttendanceInfoResponse attendances = attendanceService.getAttendances(studyId, userDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.success(attendances));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createAttendance(@PathVariable("studyId") Long studyId,
                                              @AuthenticationPrincipal Principal userDetails) {
        Long userId = userDetails.getUserId();
        Long id = attendanceService.createAttendance(studyId, userId);
        return ResponseEntity.ok(ApiResponse.success(id));
    }
}
