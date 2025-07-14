package grep.neogul_coder.domain.calender.controller;

import grep.neogul_coder.domain.calender.controller.dto.requset.TeamCalenderRequest;
import grep.neogul_coder.domain.calender.controller.dto.response.TeamCalenderResponse;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "팀 캘린더", description = "팀 캘린더 API (Swagger 인터페이스)")
public interface TeamCalenderSpecification {

    @Operation(summary = "팀 일정 전체 조회", description = "팀 모든 일정을 조회합니다.")
    ApiResponse<List<TeamCalenderResponse>> findAll(@RequestParam Long teamId);

    @Operation(summary = "팀 일정 상세 조회", description = "특정 팀 일정 상세정보를 조회합니다.")
    ApiResponse<TeamCalenderResponse> findOne(@PathVariable Long scheduleId);

    @Operation(summary = "팀 일정 생성", description = "새로운 팀 일정을 생성합니다.")
    ApiResponse<Void> create(@RequestParam Long teamId, @RequestBody TeamCalenderRequest request);


    @Operation(summary = "팀 일정 수정", description = "기존 팀 일정을 수정합니다.")
    ApiResponse<Void> update(@PathVariable Long scheduleId, @RequestBody TeamCalenderRequest request);

    @Operation(summary = "팀 일정 삭제", description = "기존 팀 일정을 삭제합니다.")
    ApiResponse<Void> delete(@PathVariable Long scheduleId);
}
