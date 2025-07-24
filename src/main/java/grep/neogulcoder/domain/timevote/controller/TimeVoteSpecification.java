package grep.neogulcoder.domain.timevote.controller;

import grep.neogulcoder.domain.timevote.dto.request.*;
import grep.neogulcoder.domain.timevote.dto.response.*;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Time-Vote", description = "스터디 모임 시간 조율 API")
public interface TimeVoteSpecification {

  @Operation(summary = "스터디 모임 일정 투표 기간 생성", description = "팀장이 가능한 시간 요청을 생성합니다.")
  ApiResponse<TimeVotePeriodResponse> createPeriod(
      @Parameter(description = "스터디 ID", example = "6") Long studyId,
      @RequestBody @Valid TimeVotePeriodCreateRequest request,
      Principal userDetails
  );

  @Operation(summary = "사용자가 제출한 시간 목록 조회", description = "해당 사용자가 이전에 제출한 시간대 목록을 조회합니다.")
  ApiResponse<TimeVoteResponse> getMyVotes(
      @Parameter(description = "스터디 ID", example = "6") Long studyId,
      Principal userDetails
  );

  @Operation(summary = "사용자 가능 시간대 제출", description = "스터디 멤버가 가능 시간을 제출합니다.")
  ApiResponse<TimeVoteResponse> submitVote(
      @Parameter(description = "스터디 ID", example = "6") Long studyId,
      @RequestBody @Valid TimeVoteCreateRequest request,
      Principal userDetails
  );

  @Operation(summary = "사용자 시간대 수정", description = "사용자가 기존에 제출한 시간을 수정합니다.")
  ApiResponse<TimeVoteResponse> updateVote(
      @Parameter(description = "스터디 ID", example = "6") Long studyId,
      @RequestBody @Valid TimeVoteUpdateRequest request,
      Principal userDetails
  );

  @Operation(summary = "사용자 전체 시간 삭제", description = "사용자가 제출한 시간 전체를 삭제합니다.")
  ApiResponse<Void> deleteAllVotes(
      @Parameter(description = "스터디 ID", example = "6") Long studyId,
      Principal userDetails
  );

  @Operation(summary = "투표 통계 조회", description = "투표 기간의 시간대별 통계 정보를 조회합니다.")
  ApiResponse<TimeVoteStatResponse> getVoteStats(
      @Parameter(description = "스터디 ID", example = "6") Long studyId,
      Principal userDetails
  );

  @Operation(summary = "사용자별 제출 여부 조회", description = "특정 스터디의 모든 멤버별 시간 제출 여부를 반환합니다.")
  ApiResponse<List<TimeVoteSubmissionStatusResponse>> getSubmissionStatusList(
      @Parameter(description = "스터디 ID", example = "6") Long studyId,
      Principal userDetails
  );
}
