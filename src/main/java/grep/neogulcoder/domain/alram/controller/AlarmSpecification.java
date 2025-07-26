package grep.neogulcoder.domain.alram.controller;

import grep.neogulcoder.domain.alram.controller.dto.response.AlarmResponse;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Tag(name = "Alarm", description = "알림 관련 API 명세")
public interface AlarmSpecification {

    @Operation(summary = "읽음 여부 관련 없이 내 전체 알림 목록 조회", description = "로그인한 사용자의 전체 알림 목록을 조회합니다.")
    ApiResponse<List<AlarmResponse>> getAllAlarms(@AuthenticationPrincipal Principal userDetails);

    @Operation(summary = "내 읽지 않은 알림 목록 조회", description = "로그인한 사용자의 읽지 않은 알림 목록을 조회합니다.")
    ApiResponse<List<AlarmResponse>> getAllUncheckedAlarm(@AuthenticationPrincipal Principal userDetails);

    @Operation(summary = "내 알림 전체 읽음 처리", description = "로그인한 사용자의 모든 알림을 읽음 처리합니다.")
    ApiResponse<Void> checkAlarm(@AuthenticationPrincipal Principal userDetails);
}
