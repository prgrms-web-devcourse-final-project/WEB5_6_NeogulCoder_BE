package grep.neogul_coder.domain.study.controller;

import grep.neogul_coder.domain.study.controller.dto.request.DelegateLeaderRequest;
import grep.neogul_coder.domain.study.controller.dto.request.ExtendStudyRequest;
import grep.neogul_coder.domain.study.controller.dto.request.StudyCreateRequest;
import grep.neogul_coder.domain.study.controller.dto.request.StudyEditRequest;
import grep.neogul_coder.domain.study.controller.dto.response.*;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Study", description = "스터디 API")
public interface StudySpecification {

    @Operation(summary = "스터디 목록 조회", description = "가입한 스터디 목록을 조회합니다.")
    ApiResponse<List<StudyListResponse>> getStudyList();

    @Operation(summary = "스터디 헤더 조회", description = "스터디 헤더 정보를 조회합니다.")
    ApiResponse<StudyHeaderResponse> getStudyHeader(Long studyId);

    @Operation(summary = "스터디 조회", description = "스터디를 조회합니다.")
    ApiResponse<StudyResponse> getStudy(Long studyId);

    @Operation(summary = "스터디 정보 조회", description = "스터디장이 스터디 정보를 조회합니다.")
    ApiResponse<StudyInfoResponse> getStudyInfo(Long studyId);

    @Operation(summary = "스터디 내 정보 조회", description = "스터디의 내 정보를 조회합니다.")
    ApiResponse<StudyMyInfoResponse> getStudyMyInfo(Long studyId);

    @Operation(summary = "스터디 생성", description = "새로운 스터디를 생성합니다.")
    ApiResponse<Void> createStudy(StudyCreateRequest request);

    @Operation(summary = "스터디 수정", description = "스터디를 수정합니다.")
    ApiResponse<Void> editStudy(Long studyId, StudyEditRequest request);

    @Operation(summary = "스터디 삭제", description = "스터디를 삭제합니다.")
    ApiResponse<Void> deleteStudy(Long studyId);

    @Operation(summary = "스터디 탈퇴", description = "스터디를 탈퇴합니다.")
    ApiResponse<Void> leaveStudy(Long studyId);

    @Operation(summary = "스터디원 강퇴", description = "스터디원을 강퇴합니다.")
    ApiResponse<Void> deleteMember(Long studyId, Long userId);

    @Operation(summary = "스터디원 위임", description = "스터디원에게 스터디장을 위임합니다.")
    ApiResponse<Void> delegateLeader(Long studyId, DelegateLeaderRequest request);

    @Operation(summary = "스터디 연장", description = "스터디장이 스터디를 연장합니다.")
    ApiResponse<Void> extendStudy(Long studyId, ExtendStudyRequest request);

    @Operation(summary = "연장 스터디 참여", description = "스터디원이 연장된 스터디에 참여합니다.")
    ApiResponse<Void> joinExtendStudy(Long studyId);
}
