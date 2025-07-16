package grep.neogul_coder.domain.calender.controller;

import grep.neogul_coder.domain.calender.controller.dto.requset.PersonalCalendarRequest;
import grep.neogul_coder.domain.calender.controller.dto.response.PersonalCalendarResponse;
import grep.neogul_coder.global.response.ApiResponse;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{userId}/calendar")
public class PersonalCalendarController implements PersonalCalendarSpecification {

    @PostMapping
    public ApiResponse<Void> create(
        @PathVariable("userId") Long userId,
        @RequestBody PersonalCalendarRequest request) {
        return ApiResponse.noContent();
    }

    @GetMapping
    public ApiResponse<List<PersonalCalendarResponse>> findAll(@PathVariable("userId") Long userId) {
        return ApiResponse.success(List.of(new PersonalCalendarResponse()));
    }


    @GetMapping("/{calendarId}")
    public ApiResponse<PersonalCalendarResponse> findOne(
        @PathVariable("userId") Long userId,
        @PathVariable("calendarId") Long calendarId
    ) {
        return ApiResponse.success(new PersonalCalendarResponse());
    }
    @GetMapping("/day")
    public ApiResponse<List<PersonalCalendarResponse>> findByDate(
        @PathVariable("userId") Long userId,
        @RequestParam String date
    ) {
        return ApiResponse.success(List.of(new PersonalCalendarResponse()));
    }

    @PutMapping("/{calendarId}")
    public ApiResponse<Void> update(
        @PathVariable("userId") Long userId,
        @PathVariable("calendarId") Long calendarId,
        @RequestBody PersonalCalendarRequest request
    ) {
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{calendarId}")
    public ApiResponse<Void> delete(
        @PathVariable("userId") Long userId,
        @PathVariable("calendarId") Long calendarId
    ) {
        return ApiResponse.noContent();
    }
}

