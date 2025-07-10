package grep.neogul_coder.domain.study.controller;

import grep.neogul_coder.domain.study.controller.dto.request.DelegateLeaderRequest;
import grep.neogul_coder.domain.study.controller.dto.request.ExtendStudyRequest;
import grep.neogul_coder.domain.study.controller.dto.request.StudyCreateRequest;
import grep.neogul_coder.domain.study.controller.dto.request.StudyEditRequest;
import grep.neogul_coder.domain.study.controller.dto.response.*;
import grep.neogul_coder.global.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/studies")
@RestController
public class StudyController implements StudySpecification {

    @GetMapping
    public ApiResponse<List<StudyListResponse>> getStudyList() {
        return ApiResponse.success(List.of(new StudyListResponse()));
    }

    @GetMapping("/{studyId}/header")
    public ApiResponse<StudyHeaderResponse> getStudyHeader(@PathVariable("studyId") Long studyId) {
        return ApiResponse.success(new StudyHeaderResponse());
    }

    @GetMapping("/{studyId}")
    public ApiResponse<StudyResponse> getStudy(@PathVariable("studyId") Long studyId) {
        return ApiResponse.success(new StudyResponse());
    }

    @GetMapping("/{studyId}/info")
    public ApiResponse<StudyInfoResponse> getStudyInfo(@PathVariable("studyId") Long studyId) {
        return ApiResponse.success(new StudyInfoResponse());
    }

    @GetMapping("/{studyId}/me")
    public ApiResponse<StudyMyInfoResponse> getStudyMyInfo(@PathVariable("studyId") Long studyId) {
        return ApiResponse.success(new StudyMyInfoResponse());
    }

    @PostMapping
    public ApiResponse<Void> createStudy(@RequestBody StudyCreateRequest request) {
        return ApiResponse.noContent();
    }

    @PutMapping("/{studyId}")
    public ApiResponse<Void> editStudy(@PathVariable("studyId") Long studyId,
                                       @RequestBody StudyEditRequest request) {
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{studyId}")
    public ApiResponse<Void> deleteStudy(@PathVariable("studyId") Long studyId) {
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{studyId}/me")
    public ApiResponse<Void> leaveStudy(@PathVariable("studyId") Long studyId) {
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{studyId}/users/{userId}")
    public ApiResponse<Void> deleteMember(@PathVariable("studyId") Long studyId,
                                        @PathVariable("userId") Long userId) {
        return ApiResponse.noContent();
    }

    @PostMapping("/{studyId}/delegate")
    public ApiResponse<Void> delegateLeader(@PathVariable("studyId") Long studyId,
                                            @RequestBody DelegateLeaderRequest request) {
        return ApiResponse.noContent();
    }

    @PostMapping("/{studyId}/extend")
    public ApiResponse<Void> extendStudy(@PathVariable("studyId") Long studyId,
                                         @RequestBody ExtendStudyRequest request) {
        return ApiResponse.noContent();
    }

    @PostMapping("/{studyId}/join")
    public ApiResponse<Void> joinExtendStudy(@PathVariable("studyId") Long studyId) {
        return ApiResponse.noContent();
    }
}
