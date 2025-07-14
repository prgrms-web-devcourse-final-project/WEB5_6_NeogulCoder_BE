package grep.neogul_coder.domain.calender.controller;

import grep.neogul_coder.domain.calender.controller.dto.requset.PersonalCalenderRequest;
import grep.neogul_coder.domain.calender.controller.dto.response.PersonalCalenderResponse;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "개인 캘린더", description = "개인 캘린더 API (Swagger 인터페이스)")
public interface PersonalCalenderSpecification {

    @Operation(summary = "개인 일정 생성", description = "사용자의 개인 일정을 생성합니다.")
    ApiResponse<Void> create(@RequestBody PersonalCalenderRequest request);

    @Operation(summary = "개인 일정 전체 조회", description = "모든 개인 일정을 조회합니다.")
    ApiResponse<List<PersonalCalenderResponse>> findAll(@RequestParam Long userId);

    @Operation(summary = "개인 일정 상세 조회", description = "개별 일정 상세정보를 조회합니다.")
    ApiResponse<PersonalCalenderResponse> findOne(@PathVariable Long scheduleId);

    @Operation(summary = "개인 일정 수정", description = "개별 일정을 수정합니다.")
    ApiResponse<Void> update(@PathVariable Long scheduleId, @RequestBody PersonalCalenderRequest request);

    @Operation(summary = "개인 일정 삭제", description = "개별 일정을 삭제합니다.")
    ApiResponse<Void> delete(@PathVariable Long scheduleId);
}
