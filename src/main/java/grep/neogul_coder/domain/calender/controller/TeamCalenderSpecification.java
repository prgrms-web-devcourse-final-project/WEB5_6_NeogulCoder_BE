package grep.neogul_coder.domain.calender.controller;

import grep.neogul_coder.domain.calender.controller.dto.requset.TeamCalenderRequest;
import grep.neogul_coder.domain.calender.controller.dto.response.TeamCalenderResponse;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "팀 캘린더", description = "팀 캘린더 API (Swagger 인터페이스)")
public interface TeamCalenderSpecification {

    @Operation(
        summary = "팀 일정 전체 조회",
        description = "특정 팀 ID에 해당하는 모든 일정을 조회합니다.\n\n" +
            "예: `/api/teams/{teamId}/calendar`"
    )
    ApiResponse<List<TeamCalenderResponse>> findAll(
        @Parameter(name = "teamId", description = "조회할 팀 ID", required = true, in = ParameterIn.PATH)
        @PathVariable("teamId") Long teamId
    );


    @Operation(
        summary = "팀 일정 상세 조회",
        description = "특정 팀의 특정 일정 상세정보를 조회합니다.\n\n" +
            "예: `/api/teams/{teamId}/calendar/{scheduleId}`"
    )
    ApiResponse<TeamCalenderResponse> findOne(
        @Parameter(name = "teamId", description = "팀 ID", required = true, in = ParameterIn.PATH)
        @PathVariable("teamId") Long teamId,
        @Parameter(name = "scheduleId", description = "일정 ID", required = true, in = ParameterIn.PATH)
        @PathVariable("scheduleId") Long scheduleId
    );


    @Operation(
        summary = "팀 일정 날짜별 조회",
        description = "특정 팀의 특정 날짜(yyyy-MM-dd)에 등록된 일정들을 조회합니다.\n\n" +
            "예: `/api/teams/{teamId}/calendar/day?date=2025-07-17`"
    )
    ApiResponse<List<TeamCalenderResponse>> findByDate(
        @Parameter(name = "teamId", description = "조회할 팀 ID", required = true, in = ParameterIn.PATH)
        @PathVariable("teamId") Long teamId,

        @Parameter(name = "date", description = "조회할 날짜 (yyyy-MM-dd)", required = true, in = ParameterIn.QUERY)
        @RequestParam String date
    );


    @Operation(
        summary = "팀 일정 생성",
        description = "특정 팀 ID에 새로운 일정을 생성합니다.\n\n" +
            "예: `/api/teams/{teamId}/calendar`"
    )
    ApiResponse<Void> create(
        @Parameter(name = "teamId", description = "일정을 생성할 팀 ID", required = true, in = ParameterIn.PATH)
        @PathVariable("teamId") Long teamId,
        @RequestBody TeamCalenderRequest request
    );

    @Operation(
        summary = "팀 일정 수정",
        description = "기존 팀 일정을 수정합니다.\n\n" +
            "예: `/api/teams/{teamId}/calendar/{scheduleId}`"
    )
    ApiResponse<Void> update(
        @Parameter(name = "teamId", description = "팀 ID", required = true, in = ParameterIn.PATH)
        @PathVariable("teamId") Long teamId,
        @Parameter(name = "scheduleId", description = "일정 ID", required = true, in = ParameterIn.PATH)
        @PathVariable("scheduleId") Long scheduleId,
        @RequestBody TeamCalenderRequest request
    );

    @Operation(
        summary = "팀 일정 삭제",
        description = "기존 팀 일정을 삭제합니다.\n\n" +
            "예: `/api/teams/{teamId}/calendar/{scheduleId}`"
    )
    ApiResponse<Void> delete(
        @Parameter(name = "teamId", description = "팀 ID", required = true, in = ParameterIn.PATH)
        @PathVariable("teamId") Long teamId,
        @Parameter(name = "scheduleId", description = "일정 ID", required = true, in = ParameterIn.PATH)
        @PathVariable("scheduleId") Long scheduleId
    );
}
