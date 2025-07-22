package grep.neogul_coder.domain.timevote.controller;

import grep.neogul_coder.domain.timevote.dto.request.TimeVoteCreateRequest;
import grep.neogul_coder.domain.timevote.dto.request.TimeVoteDeleteRequest;
import grep.neogul_coder.domain.timevote.dto.request.TimeVotePeriodCreateRequest;
import grep.neogul_coder.domain.timevote.dto.request.TimeVoteUpdateRequest;
import grep.neogul_coder.domain.timevote.dto.response.TimeVotePeriodResponse;
import grep.neogul_coder.domain.timevote.dto.response.TimeVoteResponse;
import grep.neogul_coder.domain.timevote.dto.response.TimeVoteStatListResponse;
import grep.neogul_coder.domain.timevote.dto.response.TimeVoteSubmissionStatusResponse;
import grep.neogul_coder.domain.timevote.service.TimeVotePeriodService;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studies/{studyId}/time-vote")
public class TimeVoteController implements TimeVoteSpecification {

  private final TimeVotePeriodService timeVotePeriodService;

  @PostMapping("/periods")
  public ApiResponse<TimeVotePeriodResponse> createPeriod(
      @PathVariable("studyId") Long studyId,
      @RequestBody @Valid TimeVotePeriodCreateRequest request,
      @AuthenticationPrincipal Principal userDetails
  ) {
    return ApiResponse.success(new TimeVotePeriodResponse());  // mock response
  }

  @PostMapping("/votes")
  public ApiResponse<TimeVoteResponse> submitVote(
      @PathVariable("studyId") Long studyId,
      @RequestBody @Valid TimeVoteCreateRequest request,
      @AuthenticationPrincipal Principal userDetails
  ) {
    return ApiResponse.success(new TimeVoteResponse());  // mock response
  }

  @PutMapping("/votes")
  public ApiResponse<TimeVoteResponse> updateVote(
      @PathVariable("studyId") Long studyId,
      @RequestBody @Valid TimeVoteUpdateRequest request,
      @AuthenticationPrincipal Principal userDetails
  ) {
    return ApiResponse.success(new TimeVoteResponse());  // mock response
  }

  @DeleteMapping("/votes")
  public ApiResponse<Void> deleteAllVotes(
      @PathVariable("studyId") Long studyId,
      @RequestBody @Valid TimeVoteDeleteRequest request,
      @AuthenticationPrincipal Principal userDetails
  ) {
    return ApiResponse.noContent();  // mock response
  }

  @GetMapping("/periods/stats")
  public ApiResponse<TimeVoteStatListResponse> getVoteStats(
      @PathVariable("studyId") Long studyId,
      @AuthenticationPrincipal Principal userDetails
  ) {
    return ApiResponse.success(new TimeVoteStatListResponse());  // mock response
  }

  @GetMapping("/periods/submissions")
  public ApiResponse<List<TimeVoteSubmissionStatusResponse>> getSubmissionStatusList(
      @PathVariable("studyId") Long studyId,
      @AuthenticationPrincipal Principal userDetails
  ) {
    return ApiResponse.success(Collections.emptyList());  // mock response
  }
}
