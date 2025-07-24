package grep.neogulcoder.domain.alram.controller;

import grep.neogulcoder.domain.alram.entity.Alarm;
import grep.neogulcoder.domain.alram.service.AlarmService;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alarm")
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping("/my")
    public ApiResponse<List<Alarm>> getAlarm(@AuthenticationPrincipal Principal userDetails) {
        return ApiResponse.success(alarmService.getAllAlarms(userDetails.getUserId()));
    }

    @PostMapping("/my/check")
    public ApiResponse<Void> checkAlarm(@AuthenticationPrincipal Principal userDetails) {
        alarmService.readAllAlarm(userDetails.getUserId());
        return ApiResponse.noContent();
    }

}
