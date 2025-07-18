package grep.neogul_coder.domain.studypost.controller;

import grep.neogul_coder.domain.studypost.dto.StudyPostDetailResponse;
import grep.neogul_coder.domain.studypost.dto.StudyPostListResponse;
import grep.neogul_coder.domain.studypost.dto.StudyPostRequest;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "Study-Post", description = "스터디 게시판 API")
public interface StudyPostSpecification {

  @Operation(summary = "게시글 생성", description = "스터디에 새로운 게시글을 작성합니다.")
  ApiResponse<Void> create(
      @Parameter(description = "스터디 ID", example = "1") Long studyId,
      @Valid StudyPostRequest request
  );

  @Operation(summary = "게시글 목록 전체 조회", description = "스터디의 게시글 전체 목록을 조회합니다.")
  ApiResponse<List<StudyPostListResponse>> findAllWithoutPagination(
      @Parameter(description = "스터디 ID", example = "1") Long studyId
  );

  @Operation(summary = "게시글 상세 조회", description = "특정 게시글의 상세 정보를 조회합니다.")
  ApiResponse<StudyPostDetailResponse> findOne(
      @Parameter(description = "스터디 ID", example = "1") Long studyId,
      @Parameter(description = "게시글 ID", example = "15") Long postId
  );

  @Operation(summary = "게시글 수정", description = "기존 게시글을 수정합니다.")
  ApiResponse<Void> update(
      @Parameter(description = "스터디 ID", example = "1") Long studyId,
      @Parameter(description = "게시글 ID", example = "15") Long postId,
      @Valid StudyPostRequest request
  );

  @Operation(summary = "게시글 삭제", description = "특정 게시글을 삭제합니다.")
  ApiResponse<Void> delete(
      @Parameter(description = "스터디 ID", example = "1") Long studyId,
      @Parameter(description = "게시글 ID", example = "15") Long postId
  );
}
