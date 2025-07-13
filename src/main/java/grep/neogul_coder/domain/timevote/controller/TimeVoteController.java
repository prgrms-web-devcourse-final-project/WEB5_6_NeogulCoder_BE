package grep.neogul_coder.domain.timevote.controller;

import grep.neogul_coder.domain.timevote.dto.request.*;
import grep.neogul_coder.domain.timevote.dto.response.*;
import grep.neogul_coder.global.response.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/studies/{studyId}/time-vote")
public class TimeVoteController implements TimeVoteSpecification {

  @PostMapping("/periods")
  public ApiResponse<TimeVotePeriodResponse> createPeriod(
      @PathVariable("studyId") Long studyId,
      @RequestBody @Valid TimeVotePeriodCreateRequest request
  ) {
    return ApiResponse.success(new TimeVotePeriodResponse());
  }

  @GetMapping("/periods/{periodId}/stats")
  public ApiResponse<List<TimeVoteStatResponse>> getVoteStats(
      @PathVariable("studyId") Long studyId,
      @PathVariable("periodId") Long periodId
  ) {
    return ApiResponse.success(List.of(new TimeVoteStatResponse()));
  }

  @PostMapping("/single")
  public ApiResponse<TimeVoteResponse> submitVote(
      @PathVariable("studyId") Long studyId,
      @RequestBody @Valid TimeVoteCreateRequest request
  ) {
    return ApiResponse.success(new TimeVoteResponse());
  }

  @PostMapping("/bulk")
  public ApiResponse<List<TimeVoteResponse>> submitVotes(
      @PathVariable("studyId") Long studyId,
      @RequestBody @Valid TimeVoteBulkCreateRequest request
  ) {
    return ApiResponse.success(List.of(new TimeVoteResponse()));
  }

  @PutMapping("/single")
  public ApiResponse<TimeVoteResponse> updateVote(
      @PathVariable("studyId") Long studyId,
      @RequestBody @Valid TimeVoteUpdateRequest request
  ) {
    return ApiResponse.success(new TimeVoteResponse());
  }

  @PutMapping("/bulk")
  public ApiResponse<List<TimeVoteResponse>> updateVotes(
      @PathVariable("studyId") Long studyId,
      @RequestBody @Valid TimeVoteBulkUpdateRequest request
  ) {
    return ApiResponse.success(List.of(new TimeVoteResponse()));
  }


  @DeleteMapping("/single")
  public ApiResponse<Void> deleteVotes(
      @PathVariable("studyId") Long studyId,
      @RequestBody @Valid TimeVoteDeleteRequest request
  ) {
    return ApiResponse.noContent();
  }

  @DeleteMapping("/bulk")
  public ApiResponse<Void> deleteMultipleVotes(
      @PathVariable("studyId") Long studyId,
      @RequestBody @Valid TimeVoteBulkDeleteRequest request
  ) {
    return ApiResponse.noContent();
  }

  @DeleteMapping("/all")
  public ApiResponse<Void> deleteAllVotes(
      @PathVariable("studyId") Long studyId,
      @RequestParam("periodId") Long periodId,
      @RequestParam("studyMemberId") Long studyMemberId
  ) {
    return ApiResponse.noContent();
  }
}
