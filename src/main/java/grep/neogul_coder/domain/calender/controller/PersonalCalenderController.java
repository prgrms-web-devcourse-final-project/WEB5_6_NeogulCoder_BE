package grep.neogul_coder.domain.calender.controller;

import grep.neogul_coder.domain.calender.controller.dto.requset.PersonalCalenderRequest;
import grep.neogul_coder.domain.calender.controller.dto.response.PersonalCalenderResponse;
import grep.neogul_coder.global.response.ApiResponse;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{userId}/calendar")
public class PersonalCalenderController implements PersonalCalenderSpecification {

    @PostMapping
    public ApiResponse<Void> create(
        @PathVariable("userId") Long userId,
        @RequestBody PersonalCalenderRequest request) {
        return ApiResponse.noContent();
    }

    @GetMapping
    public ApiResponse<List<PersonalCalenderResponse>> findAll(@PathVariable("userId") Long userId) {
        return ApiResponse.success(List.of(new PersonalCalenderResponse()));
    }


    @GetMapping("/{scheduleId}")
    public ApiResponse<PersonalCalenderResponse> findOne(
        @PathVariable("userId") Long userId,
        @PathVariable("scheduleId") Long scheduleId
    ) {
        return ApiResponse.success(new PersonalCalenderResponse());
    }
    @GetMapping("/day")
    public ApiResponse<List<PersonalCalenderResponse>> findByDate(
        @PathVariable("userId") Long userId,
        @RequestParam String date
    ) {
        return ApiResponse.success(List.of(new PersonalCalenderResponse()));
    }

    @PutMapping("/{scheduleId}")
    public ApiResponse<Void> update(
        @PathVariable("userId") Long userId,
        @PathVariable("scheduleId") Long scheduleId,
        @RequestBody PersonalCalenderRequest request
    ) {
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{scheduleId}")
    public ApiResponse<Void> delete(
        @PathVariable("userId") Long userId,
        @PathVariable("scheduleId") Long scheduleId
    ) {
        return ApiResponse.noContent();
    }
}

