package grep.neogul_coder.domain.study.controller;

import grep.neogul_coder.domain.study.controller.dto.request.DelegateLeaderRequest;
import grep.neogul_coder.domain.study.controller.dto.request.ExtendStudyRequest;
import grep.neogul_coder.global.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudyManagementController implements StudyManagementSpecification {

    @DeleteMapping("/{studyId}/me")
    public ApiResponse<Void> leaveStudy(@PathVariable("studyId") Long studyId) {
        return ApiResponse.noContent("스터디에서 탈퇴되었습니다.");
    }

    @DeleteMapping("/{studyId}/users/{userId}")
    public ApiResponse<Void> deleteMember(@PathVariable("studyId") Long studyId,
                                          @PathVariable("userId") Long userId) {
        return ApiResponse.noContent("해당 스터디원이 강퇴되었습니다.");
    }

    @PostMapping("/{studyId}/delegate")
    public ApiResponse<Void> delegateLeader(@PathVariable("studyId") Long studyId,
                                            @RequestBody DelegateLeaderRequest request) {
        return ApiResponse.noContent("스터디장이 위임되었습니다.");
    }

    @PostMapping("/{studyId}/extend")
    public ApiResponse<Void> extendStudy(@PathVariable("studyId") Long studyId,
                                         @RequestBody ExtendStudyRequest request) {
        return ApiResponse.noContent("스터디가 연장되었습니다.");
    }

    @PostMapping("/{studyId}/join")
    public ApiResponse<Void> joinExtendStudy(@PathVariable("studyId") Long studyId) {
        return ApiResponse.noContent("연장된 스터디에 참여하였습니다.");
    }
}
