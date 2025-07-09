package grep.neogul_coder.domain.calender.controller;

import grep.neogul_coder.domain.calender.dto.TeamCalenderSwaggerRequest;
import grep.neogul_coder.domain.calender.dto.TeamCalenderSwaggerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/calendar/team")
@Tag(name = "팀 캘린더", description = "스터디 팀 일정 관련 API")
public class TeamCalenderSwaggerController {

    @GetMapping
    @Operation(summary = "팀 일정 전체 조회", description = "모든 팀 일정을 조회합니다.")
    public ResponseEntity<List<TeamCalenderSwaggerResponse>> findAll() {
        return ResponseEntity.ok(List.of(
            new TeamCalenderSwaggerResponse(201L, 101L, "스터디 A", LocalDateTime.now(), LocalDateTime.now())
        ));
    }

    @GetMapping("/{scheduleId}")
    @Operation(summary = "팀 일정 상세 조회", description = "일정 ID로 팀 일정 상세정보를 조회합니다.")
    public ResponseEntity<TeamCalenderSwaggerResponse> findOne(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(
            new TeamCalenderSwaggerResponse(scheduleId, 101L, "스터디 A", "사이좋게 지내요 ㅠ", LocalDateTime.now(), LocalDateTime.now())
        );
    }

    @PostMapping
    @Operation(summary = "팀 일정 생성", description = "새로운 팀 일정을 생성합니다.")
    public ResponseEntity<Void> create(@RequestBody TeamCalenderSwaggerRequest request) {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{scheduleId}")
    @Operation(summary = "팀 일정 수정", description = "기존 팀 일정을 수정합니다.")
    public ResponseEntity<Void> update(@PathVariable Long scheduleId, @RequestBody TeamCalenderSwaggerRequest request) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{scheduleId}")
    @Operation(summary = "팀 일정 삭제", description = "팀 일정을 삭제합니다.")
    public ResponseEntity<Void> delete(@PathVariable Long scheduleId) {
        return ResponseEntity.noContent().build();
    }
}
