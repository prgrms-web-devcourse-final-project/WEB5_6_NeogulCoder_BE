package grep.neogul_coder.domain.calender.controller;

import grep.neogul_coder.domain.calender.controller.dto.requset.TeamCalendarRequest;
import grep.neogul_coder.domain.calender.controller.dto.response.TeamCalendarResponse;
import grep.neogul_coder.domain.calender.service.PersonalCalendarService;
import grep.neogul_coder.domain.calender.service.TeamCalendarService;
import grep.neogul_coder.global.response.ApiResponse;

import jakarta.validation.Valid;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/teams/{studyId}/calendar")
public class TeamCalendarController implements TeamCalendarSpecification {

    private final TeamCalendarService teamCalendarService;

    // 팀 일정 생성
    @PostMapping
    public ApiResponse<Void> create(
        @AuthenticationPrincipal Long userId,               // 인증된 사용자의 ID를 자동 주입받음
        @PathVariable("studyId") Long studyId,
        @Valid @RequestBody TeamCalendarRequest request
    ) {
        teamCalendarService.create(studyId, userId, request);
        return ApiResponse.noContent();
    }

    // 전체 일정 조회 API
    @GetMapping
    public ApiResponse<List<TeamCalendarResponse>> findAll(
        @AuthenticationPrincipal Long userId,
        @PathVariable("studyId") Long studyId) {
        return ApiResponse.success(teamCalendarService.findAll(studyId));
    }

    // 특정 날짜에 해당하는 팀 일정 조회 API
    @GetMapping("/day")
    public ApiResponse<List<TeamCalendarResponse>> findByDate(
        @AuthenticationPrincipal Long userId,
        @PathVariable("studyId") Long studyId,
        @RequestParam String date
    ) {
        LocalDate parsedDate = LocalDate.parse(date);
        return ApiResponse.success(teamCalendarService.findByDate(studyId, parsedDate));
    }

    // 팀 일정 수정 API - 본인이 작성한 일정만 수정 가능
    @PutMapping("/{calendarId}")
    public ApiResponse<Void> update(
        @AuthenticationPrincipal Long userId,
        @PathVariable("studyId") Long studyId,
        @PathVariable("calendarId") Long calendarId,
        @Valid @RequestBody TeamCalendarRequest request
    ) {
        teamCalendarService.update(studyId, calendarId, userId, request);
        return ApiResponse.noContent();
    }

    // 팀 일정 삭제 API - 본인이 작성한 일정만 삭제 가능
    @DeleteMapping("/{calendarId}")
    public ApiResponse<Void> delete(
        @AuthenticationPrincipal Long userId,
        @PathVariable("studyId") Long studyId,
        @PathVariable("calendarId") Long calendarId
    ) {
        teamCalendarService.delete(studyId, userId, calendarId);
        return ApiResponse.noContent();
    }
}

