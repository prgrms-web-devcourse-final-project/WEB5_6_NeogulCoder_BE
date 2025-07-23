package grep.neogul_coder.domain.timevote.controller;

import grep.neogul_coder.domain.timevote.dto.request.TimeVoteCreateRequest;
import grep.neogul_coder.domain.timevote.dto.request.TimeVotePeriodCreateRequest;
import grep.neogul_coder.domain.timevote.dto.request.TimeVoteUpdateRequest;
import grep.neogul_coder.domain.timevote.dto.response.TimeVotePeriodResponse;
import grep.neogul_coder.domain.timevote.dto.response.TimeVoteResponse;
import grep.neogul_coder.domain.timevote.dto.response.TimeVoteStatListResponse;
import grep.neogul_coder.domain.timevote.dto.response.TimeVoteSubmissionStatusResponse;
import grep.neogul_coder.domain.timevote.entity.TimeVotePeriod;
import grep.neogul_coder.domain.timevote.service.TimeVotePeriodService;
import grep.neogul_coder.domain.timevote.service.TimeVoteService;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import jakarta.validation.Valid;
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
  private final TimeVoteService timeVoteService;
//  private final TimeVoteStatService timeVoteStatService;

  @PostMapping("/periods")
  public ApiResponse<TimeVotePeriodResponse> createPeriod(
      @PathVariable("studyId") Long studyId,
      @RequestBody @Valid TimeVotePeriodCreateRequest request,
      @AuthenticationPrincipal Principal userDetails
  ) {
    TimeVotePeriod saved = timeVotePeriodService.createTimeVotePeriodAndReturn(request, studyId, userDetails.getUserId());
    return ApiResponse.success(TimeVotePeriodResponse.from(saved));
  }

  @GetMapping("/votes")
  public ApiResponse<TimeVoteResponse> getMyVotes(
      @PathVariable("studyId") Long studyId,
      @AuthenticationPrincipal Principal userDetails
  ) {
    TimeVoteResponse response = timeVoteService.getMyVotes(studyId, userDetails.getUserId());
    return ApiResponse.success(response);
  }

  @PostMapping("/votes")
  public ApiResponse<TimeVoteResponse> submitVote(
      @PathVariable("studyId") Long studyId,
      @RequestBody @Valid TimeVoteCreateRequest request,
      @AuthenticationPrincipal Principal userDetails
  ) {
    TimeVoteResponse response = timeVoteService.submitVotes(request, studyId, userDetails.getUserId());
    return ApiResponse.success(response);
  }

  @PutMapping("/votes")
  public ApiResponse<TimeVoteResponse> updateVote(
      @PathVariable("studyId") Long studyId,
      @RequestBody @Valid TimeVoteUpdateRequest request,
      @AuthenticationPrincipal Principal userDetails
  ) {
    TimeVoteResponse response = timeVoteService.updateVotes(request, studyId, userDetails.getUserId());
    return ApiResponse.success(response);
  }

  @DeleteMapping("/votes")
  public ApiResponse<Void> deleteAllVotes(
      @PathVariable("studyId") Long studyId,
      @AuthenticationPrincipal Principal userDetails
  ) {
    timeVoteService.deleteAllVotes(studyId, userDetails.getUserId());
    return ApiResponse.success("성공적으로 투표를 삭제했습니다.");
  }

  @GetMapping("/periods/submissions")
  public ApiResponse<List<TimeVoteSubmissionStatusResponse>> getSubmissionStatusList(
      @PathVariable("studyId") Long studyId,
      @AuthenticationPrincipal Principal userDetails
  ) {
    List<TimeVoteSubmissionStatusResponse> statuses = timeVoteService.getSubmissionStatusList(studyId, userDetails.getUserId());
    return ApiResponse.success(statuses);
  }

  @GetMapping("/periods/stats")
  public ApiResponse<TimeVoteStatListResponse> getVoteStats(
      @PathVariable("studyId") Long studyId,
      @AuthenticationPrincipal Principal userDetails
  ) {
    return ApiResponse.success(new TimeVoteStatListResponse());  // mock response
  }
}
