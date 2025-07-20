package grep.neogul_coder.domain.attendance.controller;

import grep.neogul_coder.domain.attendance.controller.dto.response.AttendanceResponse;
import grep.neogul_coder.domain.attendance.service.AttendanceService;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/studies/{studyId}/attendances")
@RequiredArgsConstructor
@RestController
public class AttendanceController implements AttendanceSpecification {

    private final AttendanceService attendanceService;

    @GetMapping
    public ApiResponse<List<AttendanceResponse>> getAttendances() {
        return ApiResponse.success(List.of(new AttendanceResponse()));
    }

    @PostMapping
    public ApiResponse<Long> createAttendance(@PathVariable("studyId") Long studyId,
                                              @AuthenticationPrincipal Principal userDetails) {
        Long userId = userDetails.getUserId();
        Long id = attendanceService.createAttendance(studyId, userId);
        return ApiResponse.success(id);
    }
}
