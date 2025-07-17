package grep.neogul_coder.domain.calender.controller;

import grep.neogul_coder.domain.calender.controller.dto.requset.TeamCalendarRequest;
import grep.neogul_coder.domain.calender.controller.dto.response.TeamCalendarResponse;
import grep.neogul_coder.domain.calender.service.PersonalCalendarService;
import grep.neogul_coder.domain.calender.service.TeamCalendarService;
import grep.neogul_coder.global.response.ApiResponse;

import jakarta.validation.Valid;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/teams/{studyId}/calendar")
public class TeamCalendarController implements TeamCalendarSpecification {

    private final TeamCalendarService teamCalendarService;

    @PostMapping
    public ApiResponse<Void> create(
        @PathVariable("studyId") Long studyId,
        @Valid @RequestBody TeamCalendarRequest request
    ) {
        teamCalendarService.create(studyId, request);
        return ApiResponse.noContent();
    }

    @GetMapping
    public ApiResponse<List<TeamCalendarResponse>> findAll(@PathVariable("studyId") Long studyId) {
        return ApiResponse.success(teamCalendarService.findAll(studyId));
    }

    @GetMapping("/day")
    public ApiResponse<List<TeamCalendarResponse>> findByDate(
        @PathVariable("studyId") Long studyId,
        @RequestParam String date
    ) {
        LocalDate parsedDate = LocalDate.parse(date);
        return ApiResponse.success(teamCalendarService.findByDate(studyId, parsedDate));
    }

    @PutMapping("/{calendarId}")
    public ApiResponse<Void> update(
        @PathVariable("studyId") Long studyId,
        @PathVariable("calendarId") Long calendarId,
        @Valid @RequestBody TeamCalendarRequest request
    ) {
        teamCalendarService.update(studyId, calendarId, request);
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{calendarId}")
    public ApiResponse<Void> delete(
        @PathVariable("studyId") Long studyId,
        @PathVariable("calendarId") Long calendarId
    ) {
        teamCalendarService.delete(studyId, calendarId);
        return ApiResponse.noContent();
    }
}

