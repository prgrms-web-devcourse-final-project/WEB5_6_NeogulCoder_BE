package grep.neogul_coder.domain.study.controller;

import grep.neogul_coder.domain.study.controller.dto.request.DelegateLeaderRequest;
import grep.neogul_coder.domain.study.controller.dto.request.ExtendStudyRequest;
import grep.neogul_coder.global.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/studies/{studyId}")
@RestController
public class StudyManagementController implements StudyManagementSpecification {

    @DeleteMapping("/me")
    public ApiResponse<Void> leaveStudy(@PathVariable("studyId") Long studyId) {
        return ApiResponse.noContent();
    }

    @DeleteMapping("/users/{userId}")
    public ApiResponse<Void> deleteMember(@PathVariable("studyId") Long studyId,
                                          @PathVariable("userId") Long userId) {
        return ApiResponse.noContent();
    }

    @PostMapping("/delegate")
    public ApiResponse<Void> delegateLeader(@PathVariable("studyId") Long studyId,
                                            @RequestBody DelegateLeaderRequest request) {
        return ApiResponse.noContent();
    }

    @PostMapping("/extend")
    public ApiResponse<Void> extendStudy(@PathVariable("studyId") Long studyId,
                                         @RequestBody @Valid ExtendStudyRequest request) {
        return ApiResponse.noContent();
    }

    @PostMapping("/join")
    public ApiResponse<Void> joinExtendStudy(@PathVariable("studyId") Long studyId) {
        return ApiResponse.noContent();
    }
}
