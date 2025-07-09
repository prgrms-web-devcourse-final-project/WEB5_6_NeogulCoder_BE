package grep.neogul_coder.domain.calender.controller;

import grep.neogul_coder.domain.calender.dto.PersonalCalenderSwaggerRequest;
import grep.neogul_coder.domain.calender.dto.PersonalCalenderSwaggerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calendar/personal")
@Tag(name = "개인 캘린더", description = "사용자 개인 일정 관련 API")
public class PersonalCalenderSwaggerController {

    @PostMapping
    @Operation(summary = "개인 일정 생성", description = "사용자의 개인 일정을 생성합니다.")
    public ResponseEntity<Void> create(@RequestBody PersonalCalenderSwaggerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Operation(summary = "개인 일정 전체 조회", description = "모든 개인 일정을 조회합니다.")
    public ResponseEntity<List<PersonalCalenderSwaggerResponse>> findAll() {
        return ResponseEntity.ok(List.of(
            new PersonalCalenderSwaggerResponse(1L, 1L, "코테", LocalDateTime.now(), LocalDateTime.now())

        ));
    }

    @GetMapping("/{scheduleId}")
    @Operation(summary = "개인 일정 상세 조회", description = "개별 일정 상세정보를 조회합니다.")
    public ResponseEntity<PersonalCalenderSwaggerResponse> findOne(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(
            new PersonalCalenderSwaggerResponse(scheduleId,1L, "코테", "알고리즘 공부 좀 하자", LocalDateTime.now(), LocalDateTime.now())
        );
    }

    @PutMapping("/{scheduleId}")
    @Operation(summary = "개인 일정 수정", description = "개별 일정을 수정합니다.")
    public ResponseEntity<Void> update(@PathVariable Long scheduleId, @RequestBody PersonalCalenderSwaggerRequest request) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{scheduleId}")
    @Operation(summary = "개인 일정 삭제", description = "개별 일정을 삭제합니다.")
    public ResponseEntity<Void> delete(@PathVariable Long scheduleId) {
        return ResponseEntity.noContent().build();
    }
}
