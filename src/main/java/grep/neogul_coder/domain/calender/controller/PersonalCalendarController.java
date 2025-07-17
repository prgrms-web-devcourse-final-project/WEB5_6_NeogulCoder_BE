package grep.neogul_coder.domain.calender.controller;

import grep.neogul_coder.domain.calender.controller.dto.requset.PersonalCalendarRequest;
import grep.neogul_coder.domain.calender.controller.dto.response.PersonalCalendarResponse;
import grep.neogul_coder.domain.calender.service.PersonalCalendarService;
import grep.neogul_coder.global.response.ApiResponse;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users/{userId}/calendar")
public class PersonalCalendarController implements PersonalCalendarSpecification {

    private final PersonalCalendarService personalCalendarService;

    // 사용자 개인 일정 등록 API
    @PostMapping
    public ApiResponse<Void> create(
        @PathVariable("userId") Long userId,
        @Valid @RequestBody PersonalCalendarRequest request) {
        personalCalendarService.create(userId, request);
        return ApiResponse.noContent();
    }

    // 사용자 개인 일정 전체 조회 API
    @GetMapping
    public ApiResponse<List<PersonalCalendarResponse>> findAll(@PathVariable("userId") Long userId) {
        // 해당 userId가 등록한 모든 개인 일정을 조회하여 리스트로 반환
        return ApiResponse.success(personalCalendarService.findAll(userId));
    }

    // 사용자 특정 날짜의 일정 조회 API
    @GetMapping("/day")
    public ApiResponse<List<PersonalCalendarResponse>> findByDate(
        @PathVariable("userId") Long userId,
        @RequestParam String date
    ) {
        LocalDate parsedDate = LocalDate.parse(date); // 문자열을 LocalDate 타입으로 파싱
        return ApiResponse.success(personalCalendarService.findByDate(userId, parsedDate));
    }

    @PutMapping("/{calendarId}")
    public ApiResponse<Void> update(
        @PathVariable("userId") Long userId,
        @PathVariable("calendarId") Long calendarId,
        @Valid @RequestBody PersonalCalendarRequest request
    ) {
        personalCalendarService.update(userId, calendarId, request);
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{calendarId}")
    public ApiResponse<Void> delete(
        @PathVariable("userId") Long userId,
        @PathVariable("calendarId") Long calendarId
    ) {
        personalCalendarService.delete(userId, calendarId);
        return ApiResponse.noContent();
    }
}

