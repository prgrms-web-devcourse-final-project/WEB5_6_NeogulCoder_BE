package grep.neogul_coder.domain.study.controller;

import grep.neogul_coder.domain.study.controller.dto.request.StudyCreateRequest;
import grep.neogul_coder.domain.study.controller.dto.request.StudyUpdateRequest;
import grep.neogul_coder.domain.study.controller.dto.response.*;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Study", description = "스터디 API")
public interface StudySpecification {

    @Operation(summary = "스터디 목록 조회", description = "가입한 스터디 목록을 조회합니다.")
    ApiResponse<List<StudyItemResponse>> getStudies(Principal userDetails);

    @Operation(summary = "스터디 헤더 조회", description = "스터디 헤더 정보를 조회합니다.")
    ApiResponse<StudyHeaderResponse> getStudyHeader(Long studyId);

    @Operation(summary = "스터디 조회", description = "스터디를 조회합니다.")
    ApiResponse<StudyResponse> getStudy(Long studyId);

    @Operation(summary = "스터디 대표 이미지 조회", description = "참여중인 스터디의 대표 이미지 목록을 조회합니다.")
    ApiResponse<List<StudyImageResponse>> getStudyImages(Principal userDetails);

    @Operation(summary = "스터디 정보 조회", description = "스터디장이 스터디 정보를 조회합니다.")
    ApiResponse<StudyInfoResponse> getStudyInfo(Long studyId, Principal userDetails);

    @Operation(summary = "스터디 내 정보 조회", description = "스터디의 내 정보를 조회합니다.")
    ApiResponse<StudyMyInfoResponse> getStudyMyInfo(Long studyId);

    @Operation(summary = "스터디 생성", description = "새로운 스터디를 생성합니다.")
    ApiResponse<Void> createStudy(StudyCreateRequest request, Principal userDetails);

    @Operation(summary = "스터디 수정", description = "스터디를 수정합니다.")
    ApiResponse<Void> updateStudy(Long studyId, StudyUpdateRequest request, Principal userDetails);

    @Operation(summary = "스터디 삭제", description = "스터디를 삭제합니다.")
    ApiResponse<Void> deleteStudy(Long studyId);
}
