package grep.neogul_coder.domain.attendance.controller;

import grep.neogul_coder.domain.attendance.controller.dto.response.AttendanceResponse;
import grep.neogul_coder.global.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/attendances")
@RestController
public class AttendanceController implements AttendanceSpecification {

    @GetMapping
    public ApiResponse<List<AttendanceResponse>> getAttendances() {
        return ApiResponse.success(List.of(new AttendanceResponse()));
    }

    @PostMapping
    public ApiResponse<Void> createAttendance() {
        return ApiResponse.noContent();
    }
}
