package grep.neogul_coder.domain.timevote.controller;

import grep.neogul_coder.domain.timevote.dto.request.*;
import grep.neogul_coder.domain.timevote.dto.response.*;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Time-Vote", description = "스터디 모임 최적 시간 조율 API")
public interface TimeVoteSpecification {

  @Operation(summary = "최적 시간 투표 기간 생성", description = "팀장이 가능한 시간 요청을 생성합니다.")
  ApiResponse<TimeVotePeriodResponse> createPeriod(
      @Parameter(description = "스터디 ID", example = "1") Long studyId,
      @RequestBody @Valid TimeVotePeriodCreateRequest request
  );

  @Operation(summary = "사용자 특정 가능 시간대 제출", description = "스터디 멤버가 단일 가능 시간을 제출합니다.")
  ApiResponse<TimeVoteResponse> submitVote(
      @Parameter(description = "스터디 ID", example = "1") Long studyId,
      @RequestBody @Valid TimeVoteCreateRequest request
  );

  @Operation(summary = "사용자 여러 가능 시간대 제출", description = "스터디 멤버가 여러 가능 시간을 제출합니다.")
  ApiResponse<List<TimeVoteResponse>> submitVotes(
      @Parameter(description = "스터디 ID", example = "1") Long studyId,
      @RequestBody @Valid TimeVoteBulkCreateRequest request
  );

  @Operation(summary = "사용자 특정 시간대 수정", description = "사용자가 기존 제출한 시간 중 하나를 수정합니다.")
  ApiResponse<TimeVoteResponse> updateVote(
      @Parameter(description = "스터디 ID") Long studyId,
      @RequestBody @Valid TimeVoteUpdateRequest request
  );

  @Operation(summary = "사용자 여러 시간대 수정", description = "사용자가 제출한 여러 시간대를 수정합니다.")
  ApiResponse<List<TimeVoteResponse>> updateVotes(
      @Parameter(description = "스터디 ID") Long studyId,
      @RequestBody @Valid TimeVoteBulkUpdateRequest request
  );

  @Operation(summary = "사용자 특정 시간대 삭제", description = "사용자가 특정 시간대만 삭제합니다.")
  ApiResponse<Void> deleteVotes(
      @Parameter(description = "스터디 ID") Long studyId,
      @RequestBody @Valid TimeVoteDeleteRequest request
  );

  @Operation(summary = "사용자 여러 시간대 삭제", description = "사용자가 선택한 여러 시간대를 삭제합니다.")
  ApiResponse<Void> deleteMultipleVotes(
      @Parameter(description = "스터디 ID") Long studyId,
      @RequestBody @Valid TimeVoteBulkDeleteRequest request
  );

  @Operation(summary = "사용자 전체 시간 삭제", description = "사용자가 제출한 시간 전체를 삭제합니다.")
  ApiResponse<Void> deleteAllVotes(
      @Parameter(description = "스터디 ID") Long studyId,
      @Parameter(description = "투표 기간 ID") Long periodId,
      @Parameter(description = "스터디 멤버 ID") Long studyMemberId
  );

  @Operation(summary = "투표 통계 조회", description = "특정 투표 기간의 시간대별 통계 정보를 조회합니다.")
  ApiResponse<List<TimeVoteStatResponse>> getVoteStats(
      @Parameter(description = "스터디 ID", example = "1") Long studyId,
      @Parameter(description = "투표 기간 ID", example = "5") Long periodId
  );
}
