package grep.neogul_coder.domain.calender.controller;

import grep.neogul_coder.domain.calender.controller.dto.requset.TeamCalenderRequest;
import grep.neogul_coder.domain.calender.controller.dto.response.TeamCalenderResponse;
import grep.neogul_coder.global.response.ApiResponse;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams/{teamId}/calendar")
public class TeamCalenderController implements TeamCalenderSpecification {

    @PostMapping
    public ApiResponse<Void> create(@PathVariable("teamId") Long teamId, @RequestBody TeamCalenderRequest request) {
        return ApiResponse.noContent();
    }

    @GetMapping
    public ApiResponse<List<TeamCalenderResponse>> findAll(@PathVariable("teamId") Long teamId) {
        return ApiResponse.success(List.of(new TeamCalenderResponse()));
    }

    @GetMapping("/{scheduleId}")
    public ApiResponse<TeamCalenderResponse> findOne(
        @PathVariable("teamId") Long teamId,
        @PathVariable("scheduleId") Long scheduleId
    ) {
        return ApiResponse.success(new TeamCalenderResponse());
    }


    @GetMapping("/day")
    public ApiResponse<List<TeamCalenderResponse>> findByDate(
        @PathVariable("teamId") Long teamId,
        @RequestParam String date
    ) {
        return ApiResponse.success(List.of(new TeamCalenderResponse()));
    }

    @PutMapping("/{scheduleId}")
    public ApiResponse<Void> update(
        @PathVariable("teamId") Long teamId,
        @PathVariable("scheduleId") Long scheduleId,
        @RequestBody TeamCalenderRequest request
    ) {
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{scheduleId}")
    public ApiResponse<Void> delete(
        @PathVariable("teamId") Long teamId,
        @PathVariable("scheduleId") Long scheduleId
    ) {
        return ApiResponse.noContent();
    }
}

