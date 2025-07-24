package grep.neogulcoder.domain.calender.controller;

import grep.neogulcoder.domain.calender.controller.dto.requset.PersonalCalendarRequest;
import grep.neogulcoder.domain.calender.controller.dto.response.PersonalCalendarResponse;
import grep.neogulcoder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "개인 캘린더", description = "개인 캘린더 API (Swagger 인터페이스)")
public interface PersonalCalendarSpecification {

    @Operation(
        summary = "개인 일정 생성",
        description = "사용자의 개인 일정을 생성하고, 생성된 개인 일정 ID를 반환합니다. \n\n예: `/api/users/{userId}/calendar`"
    )
    ApiResponse<Long> create(
        @Parameter(name = "userId", description = "사용자 ID", required = true, in = ParameterIn.PATH)
        @PathVariable("userId") Long userId,
        @RequestBody PersonalCalendarRequest request
    );

    @Operation(
        summary = "개인 일정 전체 조회",
        description = "사용자 ID 기준으로 모든 개인 일정을 조회합니다.\n\n예: `/api/users/{userId}/calendar`"
    )
    ApiResponse<List<PersonalCalendarResponse>> findAll(
        @Parameter(name = "userId", description = "사용자 ID", required = true, in = ParameterIn.PATH)
        @PathVariable("userId") Long userId
    );

    @Operation(
        summary = "개인 일정 날짜별 조회",
        description = "특정 사용자 ID와 날짜에 해당하는 모든 개인 일정을 조회합니다.\n\n" +
            "예: `/api/users/{userId}/calendar/day?date=2025-07-17`"
    )
    ApiResponse<List<PersonalCalendarResponse>> findByDate(
        @Parameter(name = "userId", description = "사용자 ID", required = true, in = ParameterIn.PATH)
        @PathVariable("userId") Long userId,

        @Parameter(name = "date", description = "조회할 날짜 (yyyy-MM-dd)", required = true, in = ParameterIn.QUERY)
        @RequestParam String date
    );


    @Operation(
        summary = "개인 일정 수정",
        description = "사용자의 특정 일정을 수정합니다.\n\n예: `/api/users/{userId}/calendar/{personalCalendarId}`"
    )
    ApiResponse<Void> update(
        @Parameter(name = "userId", description = "사용자 ID", required = true, in = ParameterIn.PATH)
        @PathVariable("userId") Long userId,

        @Parameter(name = "personalCalendarId", description = "개인 캘린더 ID", required = true, in = ParameterIn.PATH)
        @PathVariable("personalCalendarId") Long personalCalendarId,

        @RequestBody PersonalCalendarRequest request
    );

    @Operation(
        summary = "개인 일정 삭제",
        description = "사용자의 특정 일정을 삭제합니다.\n\n예: `/api/users/{userId}/calendar/{personalCalendarId}`"
    )
    ApiResponse<Void> delete(
        @Parameter(name = "userId", description = "사용자 ID", required = true, in = ParameterIn.PATH)
        @PathVariable("userId") Long userId,

        @Parameter(name = "personalCalendarId", description = "개인 캘린더 ID", required = true, in = ParameterIn.PATH)
        @PathVariable("personalCalendarId") Long personalCalendarId
    );
}
