package grep.neogul_coder.domain.study.controller;

import grep.neogul_coder.domain.study.controller.dto.request.DelegateLeaderRequest;
import grep.neogul_coder.domain.study.controller.dto.request.ExtendStudyRequest;
import grep.neogul_coder.domain.study.controller.dto.response.ExtendParticipationResponse;
import grep.neogul_coder.domain.study.controller.dto.response.StudyExtensionResponse;
import grep.neogul_coder.domain.study.service.StudyManagementService;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/studies/{studyId}")
@RequiredArgsConstructor
@RestController
public class StudyManagementController implements StudyManagementSpecification {

    private final StudyManagementService studyManagementService;

    @GetMapping("/extension")
    public ApiResponse<StudyExtensionResponse> getStudyExtension(@PathVariable("studyId") Long studyId) {
        return ApiResponse.success(new StudyExtensionResponse());
    }

    @GetMapping("/extension/participations")
    public ApiResponse<ExtendParticipationResponse> getExtendParticipations(@PathVariable("studyId") Long studyId) {
        return ApiResponse.success(new ExtendParticipationResponse());
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
                                         @RequestBody @Valid ExtendStudyRequest request) {
        return ApiResponse.noContent();
    }

    @PostMapping("/extension/participations")
    public ApiResponse<Void> registerExtensionParticipation(@PathVariable("studyId") Long studyId) {
        return ApiResponse.noContent();
    }
}
