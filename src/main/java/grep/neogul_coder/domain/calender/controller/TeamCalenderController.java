package grep.neogul_coder.domain.calender.controller;

import grep.neogul_coder.domain.calender.controller.dto.TeamCalenderRequest;
import grep.neogul_coder.domain.calender.controller.dto.TeamCalenderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import grep.neogul_coder.global.response.ApiResponse;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/calendar/team")
public class TeamCalenderController implements TeamCalenderSpecification {

    @GetMapping
    public ApiResponse<List<TeamCalenderResponse>> findAll() {
        return ApiResponse.success(List.of(
            new TeamCalenderResponse(201L, 101L, "스터디 A", LocalDateTime.now(), LocalDateTime.now())
        ));
    }

    @GetMapping("/{scheduleId}")
    public ApiResponse<TeamCalenderResponse> findOne(@PathVariable Long scheduleId) {
        return ApiResponse.success(
            new TeamCalenderResponse(scheduleId, 101L, "스터디 A", "깃허브 공유 및 주제 발표", LocalDateTime.now(), LocalDateTime.now())
        );
    }

    @PostMapping
    public ApiResponse<Void> create(@RequestBody TeamCalenderRequest request) {
        return ApiResponse.success(null);
    }

    @PutMapping("/{scheduleId}")
    public ApiResponse<Void> update(@PathVariable Long scheduleId, @RequestBody TeamCalenderRequest request) {
        return ApiResponse.success(null);
    }

    @DeleteMapping("/{scheduleId}")
    public ApiResponse<Void> delete(@PathVariable Long scheduleId) {
        return ApiResponse.success(null);
    }
}

