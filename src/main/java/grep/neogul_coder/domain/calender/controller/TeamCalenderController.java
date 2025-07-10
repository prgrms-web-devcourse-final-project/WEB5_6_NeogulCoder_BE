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
    public ApiResponse<Void> create(@RequestBody TeamCalenderRequest request) {
        return ApiResponse.success(null);
    }

    @GetMapping
    public ApiResponse<List<TeamCalenderResponse>> findAll() {
        return ApiResponse.success(List.of(
            TeamCalenderResponse.builder().build()
        ));
    }

    @GetMapping("/{scheduleId}")
    public ApiResponse<TeamCalenderResponse> findOne(@PathVariable Long scheduleId) {
        return ApiResponse.success(
            TeamCalenderResponse.builder().build());
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

