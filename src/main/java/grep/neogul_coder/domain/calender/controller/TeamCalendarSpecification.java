package grep.neogul_coder.domain.calender.controller;

import grep.neogul_coder.domain.calender.controller.dto.requset.TeamCalendarRequest;
import grep.neogul_coder.domain.calender.controller.dto.response.TeamCalendarResponse;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "팀 캘린더", description = "팀 캘린더 API (Swagger 인터페이스)")
public interface TeamCalendarSpecification {

    @Operation(
        summary = "팀 일정 전체 조회",
        description = "특정 팀 ID에 해당하는 모든 일정을 조회합니다.\n\n" +
            "예: `/api/teams/{studyId}/calendar`"
    )
    ApiResponse<List<TeamCalendarResponse>> findAll(
        @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
        @Parameter(name = "studyId", description = "조회할 팀 ID", required = true, in = ParameterIn.PATH)
        @PathVariable("studyId") Long studyId
    );

    @Operation(
        summary = "팀 일정 날짜별 조회",
        description = "특정 팀의 특정 날짜(yyyy-MM-dd)에 등록된 일정들을 조회합니다.\n\n" +
            "예: `/api/teams/{studyId}/calendar/day?date=2025-07-17`"
    )
    ApiResponse<List<TeamCalendarResponse>> findByDate(
        @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
        @Parameter(name = "studyId", description = "조회할 팀 ID", required = true, in = ParameterIn.PATH)
        @PathVariable("studyId") Long studyId,

        @Parameter(name = "date", description = "조회할 날짜 (yyyy-MM-dd)", required = true, in = ParameterIn.QUERY)
        @RequestParam String date
    );

    @Operation(
        summary = "팀 일정 월 단위 조회",
        description = "특정 팀의 특정 월(year, month)에 등록된 모든 일정을 조회합니다.\n\n" +
            "예: `/api/teams/{studyId}/calendar/month?year=2025&month=7`"
    )
    ApiResponse<List<TeamCalendarResponse>> findByMonth(
        @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
        @Parameter(name = "studyId", description = "조회할 팀 ID", required = true, in = ParameterIn.PATH)
        @PathVariable("studyId") Long studyId,

        @Parameter(name = "year", description = "조회할 연도 (예: 2025)", required = true, in = ParameterIn.QUERY)
        @RequestParam int year,

        @Parameter(name = "month", description = "조회할 월 (1~12)", required = true, in = ParameterIn.QUERY)
        @RequestParam int month
    );


    @Operation(
        summary = "팀 일정 생성",
        description = "팀 일정을 생성하고 생성된 팀 일정 ID를 반환합니다.\n\n" +
            "예: `/api/teams/{studyId}/calendar`"
    )
    ApiResponse<Long> create(
        @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
        @Parameter(name = "studyId", description = "일정을 생성할 팀 ID", required = true, in = ParameterIn.PATH)
        @PathVariable("studyId") Long studyId,
        @RequestBody TeamCalendarRequest request
    );

    @Operation(
        summary = "팀 일정 수정",
        description = "기존 팀 일정을 수정합니다.\n\n" +
            "예: `/api/teams/{studyId}/calendar/{teamCalendarId}`"
    )
    ApiResponse<Void> update(
        @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
        @Parameter(name = "studyId", description = "팀 ID", required = true, in = ParameterIn.PATH)
        @PathVariable("studyId") Long studyId,
        @Parameter(name = "teamCalendarId", description = "팀 캘린더 ID", required = true, in = ParameterIn.PATH)
        @PathVariable("teamCalendarId") Long teamCalendarId,
        @RequestBody TeamCalendarRequest request
    );

    @Operation(
        summary = "팀 일정 삭제",
        description = "기존 팀 일정을 삭제합니다.\n\n" +
            "예: `/api/teams/{studyId}/calendar/{teamCalendarId}`"
    )
    ApiResponse<Void> delete(
        @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
        @Parameter(name = "studyId", description = "팀 ID", required = true, in = ParameterIn.PATH)
        @PathVariable("studyId") Long studyId,
        @Parameter(name = "teamCalendarId", description = "팀 캘린더 ID", required = true, in = ParameterIn.PATH)
        @PathVariable("teamCalendarId") Long teamCalendarId
    );
}
