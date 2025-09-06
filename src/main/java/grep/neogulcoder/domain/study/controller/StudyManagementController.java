package grep.neogulcoder.domain.study.controller;

import grep.neogulcoder.domain.study.controller.dto.request.DelegateLeaderRequest;
import grep.neogulcoder.domain.study.controller.dto.request.ExtendStudyRequest;
import grep.neogulcoder.domain.study.controller.dto.response.ExtendParticipationResponse;
import grep.neogulcoder.domain.study.controller.dto.response.StudyExtensionResponse;
import grep.neogulcoder.domain.study.service.StudyManagementService;
import grep.neogulcoder.domain.study.service.StudyManagementServiceFacade;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/studies/{studyId}")
@RequiredArgsConstructor
@RestController
public class StudyManagementController implements StudyManagementSpecification {

    private final StudyManagementService studyManagementService;
    private final StudyManagementServiceFacade studyManagementServiceFacade;

    @GetMapping("/extension")
    public ResponseEntity<ApiResponse<StudyExtensionResponse>> getStudyExtension(@PathVariable("studyId") Long studyId) {
        StudyExtensionResponse studyExtension = studyManagementService.getStudyExtension(studyId);
        return ResponseEntity.ok(ApiResponse.success(studyExtension));
    }

    @GetMapping("/extension/participations")
    public ResponseEntity<ApiResponse<List<ExtendParticipationResponse>>> getExtendParticipations(@PathVariable("studyId") Long studyId) {
        List<ExtendParticipationResponse> extendParticipations = studyManagementService.getExtendParticipations(studyId);
        return ResponseEntity.ok(ApiResponse.success(extendParticipations));
    }

    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<Void>> leaveStudy(@PathVariable("studyId") Long studyId,
                                        @AuthenticationPrincipal Principal userDetails) {
        studyManagementServiceFacade.leaveStudyWithRetry(studyId, userDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    @PostMapping("/delegate")
    public ResponseEntity<ApiResponse<Void>> delegateLeader(@PathVariable("studyId") Long studyId,
                                            @RequestBody DelegateLeaderRequest request,
                                            @AuthenticationPrincipal Principal userDetails) {
        studyManagementService.delegateLeader(studyId, userDetails.getUserId(), request.getNewLeaderId());
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    @PostMapping("/extension")
    public ResponseEntity<ApiResponse<Long>> extendStudy(@PathVariable("studyId") Long studyId,
                                         @RequestBody @Valid ExtendStudyRequest request,
                                         @AuthenticationPrincipal Principal userDetails) {
        Long extendStudyId = studyManagementService.extendStudy(studyId, request, userDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.success(extendStudyId));
    }

    @PostMapping("/extension/participations")
    public ResponseEntity<ApiResponse<Void>> registerExtensionParticipation(@PathVariable("studyId") Long studyId,
                                                            @AuthenticationPrincipal Principal userDetails) {
        studyManagementServiceFacade.registerExtensionParticipationWithRetry(studyId, userDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    @PostMapping("/invite/user")
    public ResponseEntity<ApiResponse<Void>> inviteUser(@PathVariable("studyId") Long studyId, @AuthenticationPrincipal Principal userDetails, @RequestParam String targetUserNickname) {
        studyManagementService.inviteTargetUser(studyId, userDetails.getUserId(), targetUserNickname);
        return ResponseEntity.ok(ApiResponse.noContent());
    }
}
