package grep.neogul_coder.domain.calender.controller;

import grep.neogul_coder.domain.calender.controller.dto.requset.PersonalCalenderRequest;
import grep.neogul_coder.domain.calender.controller.dto.response.PersonalCalenderResponse;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "개인 캘린더", description = "개인 캘린더 API (Swagger 인터페이스)")
public interface PersonalCalenderSpecification {

    @Operation(
        summary = "개인 일정 생성",
        description = "사용자의 개인 일정을 생성합니다.\n\n예: `/api/calendar/personal?userId=123`"
    )
    ApiResponse<Void> create(
        @Parameter(name = "userId", description = "일정을 생성할 사용자 ID", required = true, in = ParameterIn.QUERY)
        @RequestParam Long userId,
        @RequestBody PersonalCalenderRequest request
    );

    @Operation(
        summary = "개인 일정 전체 조회",
        description = "사용자 ID 기준으로 모든 개인 일정을 조회합니다.\n\n예: `/api/calendar/personal?userId=123`"
    )
    ApiResponse<List<PersonalCalenderResponse>> findAll(
        @Parameter(name = "userId", description = "일정을 조회할 사용자 ID", required = true, in = ParameterIn.QUERY)
        @RequestParam Long userId
    );

    @Operation(
        summary = "개인 일정 상세 조회",
        description = "사용자의 특정 일정 상세정보를 조회합니다.\n\n예: `/api/calendar/personal/{scheduleId}?userId=123`"
    )
    ApiResponse<PersonalCalenderResponse> findOne(
        @PathVariable Long scheduleId,
        @Parameter(name = "userId", description = "사용자 ID", required = true, in = ParameterIn.QUERY)
        @RequestParam Long userId
    );

    @Operation(
        summary = "개인 일정 날짜별 조회",
        description = "특정 사용자 ID와 날짜에 해당하는 모든 개인 일정을 조회합니다.\n\n" +
            "예: `/api/calendar/personal/day?userId=1&date=2025-07-17`"
    )
    ApiResponse<List<PersonalCalenderResponse>> findByDate(
        @Parameter(name = "userId", description = "사용자 ID", required = true, in = ParameterIn.QUERY)
        @RequestParam Long userId,

        @Parameter(name = "date", description = "조회할 날짜 (예: 2025-07-17)", required = true, in = ParameterIn.QUERY)
        @RequestParam String date
    );


    @Operation(
        summary = "개인 일정 수정",
        description = "사용자의 특정 일정을 수정합니다.\n\n예: `/api/calendar/personal/{scheduleId}?userId=123`"
    )
    ApiResponse<Void> update(
        @PathVariable Long scheduleId,
        @Parameter(name = "userId", description = "사용자 ID", required = true, in = ParameterIn.QUERY)
        @RequestParam Long userId,
        @RequestBody PersonalCalenderRequest request
    );

    @Operation(
        summary = "개인 일정 삭제",
        description = "사용자의 특정 일정을 삭제합니다.\n\n예: `/api/calendar/personal/{scheduleId}?userId=123`"
    )
    ApiResponse<Void> delete(
        @PathVariable Long scheduleId,
        @Parameter(name = "userId", description = "사용자 ID", required = true, in = ParameterIn.QUERY)
        @RequestParam Long userId
    );
}
