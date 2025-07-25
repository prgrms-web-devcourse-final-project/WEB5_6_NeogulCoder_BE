package grep.neogulcoder.domain.alram.controller;

import grep.neogulcoder.domain.alram.controller.dto.response.AlarmResponse;
import grep.neogulcoder.domain.alram.service.AlarmService;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alarm")
public class AlarmController implements AlarmSpecification {

    private final AlarmService alarmService;

    @GetMapping("/my")
    public ApiResponse<List<AlarmResponse>> getAllAlarm(
        @AuthenticationPrincipal Principal userDetails) {
        return ApiResponse.success(alarmService.getAllAlarms(userDetails.getUserId()));
    }

    @PostMapping("/my/check/all")
    public ApiResponse<Void> checkAlarm(@AuthenticationPrincipal Principal userDetails) {
        alarmService.checkAllAlarm(userDetails.getUserId());
        return ApiResponse.noContent();
    }

    @PostMapping("/choose/{alarmId}/response")
    public ApiResponse<Void> respondToInvite(@AuthenticationPrincipal Principal principal,
        @PathVariable Long alarmId,
        boolean accepted) {
        if (accepted) {
            alarmService.acceptInvite(principal.getUserId(), alarmId);
        } else {
            alarmService.rejectInvite(principal.getUserId());
        }

        return ApiResponse.success(accepted ? "스터디 초대를 수락했습니다." : "스터디 초대를 거절했습니다.");
    }

}
