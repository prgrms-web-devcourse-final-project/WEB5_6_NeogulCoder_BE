package grep.neogulcoder.domain.study.controller;

import grep.neogulcoder.domain.study.controller.dto.request.StudyCreateRequest;
import grep.neogulcoder.domain.study.controller.dto.request.StudyUpdateRequest;
import grep.neogulcoder.domain.study.controller.dto.response.*;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "Study", description = "스터디 API")
public interface StudySpecification {

    @Operation(summary = "스터디 목록 조회", description = "가입한 스터디 목록을 조회합니다.")
    ResponseEntity<ApiResponse<StudyItemPagingResponse>> getMyStudies(Pageable pageable, Boolean finished, Principal userDetails);

    @Operation(summary = "종료되지않은 내 스터디 목록 조회", description = "종료되지않은 내 스터디 목록을 조회합니다.")
    ResponseEntity<ApiResponse<List<StudyItemResponse>>> getMyUnfinishedStudies(Principal userDetails);

    @Operation(summary = "스터디 헤더 조회", description = "스터디 헤더 정보를 조회합니다.")
    ResponseEntity<ApiResponse<StudyHeaderResponse>> getStudyHeader(Long studyId);

    @Operation(summary = "스터디 조회", description = "스터디를 조회합니다.")
    ResponseEntity<ApiResponse<StudyResponse>> getStudy(Long studyId);

    @Operation(summary = "스터디 대표 이미지 조회", description = "참여중인 스터디의 대표 이미지 목록을 조회합니다.")
    ResponseEntity<ApiResponse<List<StudyImageResponse>>> getStudyImages(Principal userDetails);

    @Operation(summary = "스터디 정보 조회", description = "스터디장이 스터디 정보를 조회합니다.")
    ResponseEntity<ApiResponse<StudyInfoResponse>> getStudyInfo(Long studyId, Principal userDetails);

    @Operation(summary = "스터디 내 정보 조회", description = "스터디에서 사용자의 정보를 조회합니다.")
    ResponseEntity<ApiResponse<StudyMemberInfoResponse>> getMyStudyMemberInfo(Long studyId, Principal userDetails);

    @Operation(summary = "스터디 생성", description = "새로운 스터디를 생성합니다.")
    ResponseEntity<ApiResponse<Long>> createStudy(StudyCreateRequest request, MultipartFile image, Principal userDetails) throws IOException;

    @Operation(summary = "스터디 수정", description = "스터디를 수정합니다.")
    ResponseEntity<ApiResponse<Void>> updateStudy(Long studyId, StudyUpdateRequest request, MultipartFile image, Principal userDetails) throws IOException;

    @Operation(summary = "스터디 삭제", description = "스터디를 삭제합니다.")
    ResponseEntity<ApiResponse<Void>> deleteStudy(Long studyId, Principal userDetails);
}
