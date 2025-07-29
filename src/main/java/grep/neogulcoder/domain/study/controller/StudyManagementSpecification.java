package grep.neogulcoder.domain.study.controller;

import grep.neogulcoder.domain.study.controller.dto.request.DelegateLeaderRequest;
import grep.neogulcoder.domain.study.controller.dto.request.ExtendStudyRequest;
import grep.neogulcoder.domain.study.controller.dto.response.ExtendParticipationResponse;
import grep.neogulcoder.domain.study.controller.dto.response.StudyExtensionResponse;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "StudyManagement", description = "스터디 관리 API")
public interface StudyManagementSpecification {

    @Operation(summary = "스터디 연장 여부 조회", description = "스터디 연장 여부를 조회합니다.")
    ResponseEntity<ApiResponse<StudyExtensionResponse>> getStudyExtension(Long studyId);

    @Operation(summary = "연장 스터디 참여 멤버 목록 조회", description = "연장 스터디에 참여하는 멤버 목록을 조회합니다.")
    ResponseEntity<ApiResponse<List<ExtendParticipationResponse>>> getExtendParticipations(Long studyId);

    @Operation(summary = "스터디 탈퇴", description = "스터디를 탈퇴합니다.")
    ResponseEntity<ApiResponse<Void>> leaveStudy(Long studyId, Principal userDetails);

    @Operation(summary = "스터디장 위임", description = "스터디원에게 스터디장을 위임합니다.")
    ResponseEntity<ApiResponse<Void>> delegateLeader(Long studyId, DelegateLeaderRequest request, Principal userDetails);

    @Operation(summary = "스터디 연장", description = "스터디장이 스터디를 연장합니다.")
    ResponseEntity<ApiResponse<Long>> extendStudy(Long studyId, ExtendStudyRequest request, Principal userDetails);

    @Operation(summary = "연장 스터디 참여", description = "스터디원이 연장된 스터디에 참여합니다.")
    ResponseEntity<ApiResponse<Void>> registerExtensionParticipation(Long studyId, Principal userDetails);

    @Operation(summary = "스터디 초대", description = "스터디장이 스터디에 초대합니다.")
    ResponseEntity<ApiResponse<Void>> inviteUser(Long studyId, Principal userDetails, String targetUserNickname);
}
