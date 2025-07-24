package grep.neogulcoder.domain.study.controller;

import grep.neogulcoder.domain.study.controller.dto.request.DelegateLeaderRequest;
import grep.neogulcoder.domain.study.controller.dto.request.ExtendStudyRequest;
import grep.neogulcoder.domain.study.controller.dto.response.ExtendParticipationResponse;
import grep.neogulcoder.domain.study.controller.dto.response.StudyExtensionResponse;
import grep.neogulcoder.domain.study.service.StudyManagementService;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/studies/{studyId}")
@RequiredArgsConstructor
@RestController
public class StudyManagementController implements StudyManagementSpecification {

    private final StudyManagementService studyManagementService;

    @GetMapping("/extension")
    public ApiResponse<StudyExtensionResponse> getStudyExtension(@PathVariable("studyId") Long studyId) {
        StudyExtensionResponse studyExtension = studyManagementService.getStudyExtension(studyId);
        return ApiResponse.success(studyExtension);
    }

    @GetMapping("/extension/participations")
    public ApiResponse<List<ExtendParticipationResponse>> getExtendParticipations(@PathVariable("studyId") Long studyId) {
        List<ExtendParticipationResponse> extendParticipations = studyManagementService.getExtendParticipations(studyId);
        return ApiResponse.success(extendParticipations);
    }

    @DeleteMapping("/me")
    public ApiResponse<Void> leaveStudy(@PathVariable("studyId") Long studyId,
                                        @AuthenticationPrincipal Principal userDetails) {
        studyManagementService.leaveStudy(studyId, userDetails.getUserId());
        return ApiResponse.noContent();
    }

    @PostMapping("/delegate")
    public ApiResponse<Void> delegateLeader(@PathVariable("studyId") Long studyId,
                                            @RequestBody DelegateLeaderRequest request,
                                            @AuthenticationPrincipal Principal userDetails) {
        studyManagementService.delegateLeader(studyId, userDetails.getUserId(), request.getNewLeaderId());
        return ApiResponse.noContent();
    }

    @PostMapping("/extension")
    public ApiResponse<Void> extendStudy(@PathVariable("studyId") Long studyId,
                                         @RequestBody @Valid ExtendStudyRequest request,
                                         @AuthenticationPrincipal Principal userDetails) {
        studyManagementService.extendStudy(studyId, request, userDetails.getUserId());
        return ApiResponse.noContent();
    }

    @PostMapping("/extension/participations")
    public ApiResponse<Void> registerExtensionParticipation(@PathVariable("studyId") Long studyId,
                                                            @AuthenticationPrincipal Principal userDetails) {
        studyManagementService.registerExtensionParticipation(studyId, userDetails.getUserId());
        return ApiResponse.noContent();
    }

    @PostMapping("/invite/user")
    public ApiResponse<Void> inviteUser(@PathVariable("studyId") Long studyId, @AuthenticationPrincipal Principal userDetails, String targetUserNickname) {
        studyManagementService.inviteTargetUser(studyId, userDetails.getUserId(), targetUserNickname);
        return ApiResponse.noContent();
    }

    @PostMapping("/accept/invite")
    public ApiResponse<Void> acceptInvite(@PathVariable("studyId") Long studyId,@AuthenticationPrincipal Principal principal) {
        studyManagementService.acceptInvite(studyId, principal.getUserId());
        return ApiResponse.success("스터디 초대를 수락했습니다.");
    }
}
