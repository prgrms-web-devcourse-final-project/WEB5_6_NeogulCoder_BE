package grep.neogul_coder.domain.calender.controller;

import grep.neogul_coder.domain.calender.controller.dto.requset.TeamCalendarRequest;
import grep.neogul_coder.domain.calender.controller.dto.response.TeamCalendarResponse;
import grep.neogul_coder.global.response.ApiResponse;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams/{studyId}/calendar")
public class TeamCalendarController implements TeamCalendarSpecification {

    @PostMapping
    public ApiResponse<Void> create(@PathVariable("studyId") Long studyId, @RequestBody TeamCalendarRequest request) {
        return ApiResponse.noContent();
    }

    @GetMapping
    public ApiResponse<List<TeamCalendarResponse>> findAll(@PathVariable("studyId") Long studyId) {
        return ApiResponse.success(List.of(new TeamCalendarResponse()));
    }

    @GetMapping("/{calendarId}")
    public ApiResponse<TeamCalendarResponse> findOne(
        @PathVariable("studyId") Long studyId,
        @PathVariable("calendarId") Long calendarId
    ) {
        return ApiResponse.success(new TeamCalendarResponse());
    }


    @GetMapping("/day")
    public ApiResponse<List<TeamCalendarResponse>> findByDate(
        @PathVariable("studyId") Long studyId,
        @RequestParam String date
    ) {
        return ApiResponse.success(List.of(new TeamCalendarResponse()));
    }

    @PutMapping("/{calendarId}")
    public ApiResponse<Void> update(
        @PathVariable("studyId") Long studyId,
        @PathVariable("calendarId") Long calendarId,
        @RequestBody TeamCalendarRequest request
    ) {
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{calendarId}")
    public ApiResponse<Void> delete(
        @PathVariable("studyId") Long studyId,
        @PathVariable("calendarId") Long calendarId
    ) {
        return ApiResponse.noContent();
    }
}

