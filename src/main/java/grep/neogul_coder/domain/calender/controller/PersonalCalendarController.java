package grep.neogul_coder.domain.calender.controller;

import grep.neogul_coder.domain.calender.controller.dto.requset.PersonalCalendarRequest;
import grep.neogul_coder.domain.calender.controller.dto.response.PersonalCalendarResponse;
import grep.neogul_coder.domain.calender.service.PersonalCalendarService;
import grep.neogul_coder.global.response.ApiResponse;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users/{userId}/calendar")
public class PersonalCalendarController implements PersonalCalendarSpecification {

    private final PersonalCalendarService personalCalendarService;

    @PostMapping
    public ApiResponse<Void> create(
        @PathVariable("userId") Long userId,
        @RequestBody PersonalCalendarRequest request) {
        personalCalendarService.create(userId, request);
        return ApiResponse.noContent();
    }

    @GetMapping
    public ApiResponse<List<PersonalCalendarResponse>> findAll(@PathVariable("userId") Long userId) {
        return ApiResponse.success(personalCalendarService.findAll(userId));
    }

    @GetMapping("/day")
    public ApiResponse<List<PersonalCalendarResponse>> findByDate(
        @PathVariable("userId") Long userId,
        @RequestParam String date
    ) {
        LocalDate parsedDate = LocalDate.parse(date);
        return ApiResponse.success(personalCalendarService.findByDate(userId, parsedDate));
    }

    @PutMapping("/{calendarId}")
    public ApiResponse<Void> update(
        @PathVariable("userId") Long userId,
        @PathVariable("calendarId") Long calendarId,
        @RequestBody PersonalCalendarRequest request
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

