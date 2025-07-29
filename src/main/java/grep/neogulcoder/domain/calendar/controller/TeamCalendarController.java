package grep.neogulcoder.domain.calendar.controller;

import grep.neogulcoder.domain.calendar.controller.dto.requset.TeamCalendarRequest;
import grep.neogulcoder.domain.calendar.controller.dto.response.TeamCalendarResponse;
import grep.neogulcoder.domain.calendar.service.TeamCalendarService;
import grep.neogulcoder.global.response.ApiResponse;

import jakarta.validation.Valid;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<Long>> create(
        @AuthenticationPrincipal(expression = "userId") Long userId,              // 인증된 사용자의 ID를 자동 주입받음
        @PathVariable("studyId") Long studyId,
        @Valid @RequestBody TeamCalendarRequest request
    ) {
        Long teamCalendarId = teamCalendarService.create(studyId, userId, request);
        return ResponseEntity.ok(ApiResponse.success(teamCalendarId));  // 생성된 일정 ID 반환
    }

    // 전체 일정 조회 API
    @GetMapping
    public ResponseEntity<ApiResponse<List<TeamCalendarResponse>>> findAll(
        @AuthenticationPrincipal(expression = "userId") Long userId,
        @PathVariable("studyId") Long studyId) {
        return ResponseEntity.ok(ApiResponse.success(teamCalendarService.findAll(studyId)));
    }

    // 특정 날짜에 해당하는 팀 일정 조회 API
    @GetMapping("/day")
    public ResponseEntity<ApiResponse<List<TeamCalendarResponse>>> findByDate(
        @AuthenticationPrincipal(expression = "userId") Long userId,
        @PathVariable("studyId") Long studyId,
        @RequestParam String date
    ) {
        LocalDate parsedDate = LocalDate.parse(date);
        return ResponseEntity.ok(ApiResponse.success(teamCalendarService.findByDate(studyId, parsedDate)));
    }

    // 특정 월(한 달 단위) 팀 일정 조회 API
    @GetMapping("/month")
    public ResponseEntity<ApiResponse<List<TeamCalendarResponse>>> findByMonth(
        @AuthenticationPrincipal(expression = "userId") Long userId,
        @PathVariable("studyId") Long studyId,
        @RequestParam int year,
        @RequestParam int month
    ) {
        return ResponseEntity.ok(ApiResponse.success(teamCalendarService.findByMonth(studyId, year, month)));
    }

    // 팀 일정 수정 API - 본인이 작성한 일정만 수정 가능
    @PutMapping("/{teamCalendarId}")
    public ResponseEntity<ApiResponse<Void>> update(
        @AuthenticationPrincipal(expression = "userId") Long userId,
        @PathVariable("studyId") Long studyId,
        @PathVariable("teamCalendarId") Long teamCalendarId,
        @Valid @RequestBody TeamCalendarRequest request
    ) {
        teamCalendarService.update(studyId, userId, teamCalendarId, request);
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    // 팀 일정 삭제 API - 본인이 작성한 일정만 삭제 가능
    @DeleteMapping("/{teamCalendarId}")
    public ResponseEntity<ApiResponse<Void>> delete(
        @AuthenticationPrincipal(expression = "userId") Long userId,
        @PathVariable("studyId") Long studyId,
        @PathVariable("teamCalendarId") Long teamCalendarId
    ) {
        teamCalendarService.delete(studyId, userId, teamCalendarId);
        return ResponseEntity.ok(ApiResponse.noContent());
    }
}

