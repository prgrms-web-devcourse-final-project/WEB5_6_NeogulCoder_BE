package grep.neogulcoder.domain.calendar.controller;

import grep.neogulcoder.domain.calendar.controller.dto.requset.PersonalCalendarRequest;
import grep.neogulcoder.domain.calendar.controller.dto.response.PersonalCalendarResponse;
import grep.neogulcoder.domain.calendar.service.PersonalCalendarService;
import grep.neogulcoder.global.response.ApiResponse;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users/{userId}/calendar")
public class PersonalCalendarController implements PersonalCalendarSpecification {

    private final PersonalCalendarService personalCalendarService;

    // 사용자 개인 일정 등록 API
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> create(
        @PathVariable("userId") Long userId,
        @Valid @RequestBody PersonalCalendarRequest request) {
        Long personalCalendarId  = personalCalendarService.create(userId, request);
        return ResponseEntity.ok(ApiResponse.success(personalCalendarId));  // 생성된 일정 ID 반환
    }

    // 사용자 개인 일정 전체 조회 API
    @GetMapping
    public ResponseEntity<ApiResponse<List<PersonalCalendarResponse>>> findAll(@PathVariable("userId") Long userId) {
        // 해당 userId가 등록한 모든 개인 일정을 조회하여 리스트로 반환
        return ResponseEntity.ok(ApiResponse.success(personalCalendarService.findAll(userId)));
    }

    // 사용자 특정 날짜의 일정 조회 API
    @GetMapping("/day")
    public ResponseEntity<ApiResponse<List<PersonalCalendarResponse>>> findByDate(
        @PathVariable("userId") Long userId,
        @RequestParam String date
    ) {
        LocalDate parsedDate = LocalDate.parse(date); // 문자열을 LocalDate 타입으로 파싱
        return ResponseEntity.ok(ApiResponse.success(personalCalendarService.findByDate(userId, parsedDate)));
    }

    @PutMapping("/{personalCalendarId}")
    public ResponseEntity<ApiResponse<Void>> update(
        @PathVariable("userId") Long userId,
        @PathVariable("personalCalendarId") Long personalCalendarId,
        @Valid @RequestBody PersonalCalendarRequest request
    ) {
        personalCalendarService.update(userId, personalCalendarId, request);
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    @DeleteMapping("/{personalCalendarId}")
    public ResponseEntity<ApiResponse<Void>> delete(
        @PathVariable("userId") Long userId,
        @PathVariable("personalCalendarId") Long personalCalendarId
    ) {
        personalCalendarService.delete(userId, personalCalendarId);
        return ResponseEntity.ok(ApiResponse.noContent());
    }
}

