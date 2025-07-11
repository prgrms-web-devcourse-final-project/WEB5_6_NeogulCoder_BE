package grep.neogul_coder.domain.calender.controller;

import grep.neogul_coder.domain.calender.controller.dto.requset.PersonalCalenderRequest;
import grep.neogul_coder.domain.calender.controller.dto.response.PersonalCalenderResponse;
import grep.neogul_coder.global.response.ApiResponse;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calendar/personal")
public class PersonalCalenderController implements PersonalCalenderSpecification {

    @PostMapping
    public ApiResponse<Void> create(@RequestBody PersonalCalenderRequest request) {
        return ApiResponse.success(null);
    }

    @GetMapping
    public ApiResponse<List<PersonalCalenderResponse>> findAll() {
        return ApiResponse.success(List.of(
            PersonalCalenderResponse.builder().build()
        ));
    }

    @GetMapping("/{scheduleId}")
    public ApiResponse<PersonalCalenderResponse> findOne(@PathVariable Long scheduleId) {
        return ApiResponse.success(
            PersonalCalenderResponse.builder().build());
    }

    @PutMapping("/{scheduleId}")
    public ApiResponse<Void> update(@PathVariable Long scheduleId, @RequestBody PersonalCalenderRequest request) {
        return ApiResponse.success(null);
    }

    @DeleteMapping("/{scheduleId}")
    public ApiResponse<Void> delete(@PathVariable Long scheduleId) {
        return ApiResponse.success(null);
    }
}

