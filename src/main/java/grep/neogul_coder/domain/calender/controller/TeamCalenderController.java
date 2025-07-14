package grep.neogul_coder.domain.calender.controller;

import grep.neogul_coder.domain.calender.controller.dto.requset.TeamCalenderRequest;
import grep.neogul_coder.domain.calender.controller.dto.response.TeamCalenderResponse;
import grep.neogul_coder.global.response.ApiResponse;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calendar/team")
public class TeamCalenderController implements TeamCalenderSpecification {

    @PostMapping
    public ApiResponse<Void> create(@RequestParam Long teamId, @RequestBody TeamCalenderRequest request) {
        return ApiResponse.noContent();
    }

    @GetMapping
    public ApiResponse<List<TeamCalenderResponse>> findAll(@RequestParam Long teamId) {
        return ApiResponse.success(List.of(new TeamCalenderResponse()));
    }

    @GetMapping("/{scheduleId}")
    public ApiResponse<TeamCalenderResponse> findOne(@PathVariable Long scheduleId) {
        return ApiResponse.success(new TeamCalenderResponse());
    }

    @PutMapping("/{scheduleId}")
    public ApiResponse<Void> update(@PathVariable Long scheduleId, @RequestBody TeamCalenderRequest request) {
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{scheduleId}")
    public ApiResponse<Void> delete(@PathVariable Long scheduleId) {
        return ApiResponse.noContent();
    }
}

