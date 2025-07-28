package grep.neogulcoder.domain.timevote.controller;

import grep.neogulcoder.domain.timevote.dto.request.TimeVoteCreateRequest;
import grep.neogulcoder.domain.timevote.dto.request.TimeVotePeriodCreateRequest;
import grep.neogulcoder.domain.timevote.dto.request.TimeVoteUpdateRequest;
import grep.neogulcoder.domain.timevote.dto.response.TimeVotePeriodResponse;
import grep.neogulcoder.domain.timevote.dto.response.TimeVoteResponse;
import grep.neogulcoder.domain.timevote.dto.response.TimeVoteStatResponse;
import grep.neogulcoder.domain.timevote.dto.response.TimeVoteSubmissionStatusResponse;
import grep.neogulcoder.domain.timevote.service.period.TimeVotePeriodService;
import grep.neogulcoder.domain.timevote.service.stat.TimeVoteStatService;
import grep.neogulcoder.domain.timevote.service.vote.TimeVoteService;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  private final TimeVoteStatService timeVoteStatService;

  @PostMapping("/periods")
  public ResponseEntity<ApiResponse<TimeVotePeriodResponse>> createPeriod(
      @PathVariable("studyId") Long studyId,
      @RequestBody @Valid TimeVotePeriodCreateRequest request,
      @AuthenticationPrincipal Principal userDetails
  ) {
    TimeVotePeriodResponse response = timeVotePeriodService.createTimeVotePeriodAndReturn(
        request, studyId, userDetails.getUserId()
    );
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
  }

  @GetMapping("/votes")
  public ResponseEntity<ApiResponse<TimeVoteResponse>> getMyVotes(
      @PathVariable("studyId") Long studyId,
      @AuthenticationPrincipal Principal userDetails
  ) {
    TimeVoteResponse response = timeVoteService.getMyVotes(studyId, userDetails.getUserId());
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @PostMapping("/votes")
  public ResponseEntity<ApiResponse<TimeVoteResponse>> submitVote(
      @PathVariable("studyId") Long studyId,
      @RequestBody @Valid TimeVoteCreateRequest request,
      @AuthenticationPrincipal Principal userDetails
  ) {
    TimeVoteResponse response = timeVoteService.submitVotes(request, studyId,
        userDetails.getUserId());
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
  }

  @PutMapping("/votes")
  public ResponseEntity<ApiResponse<TimeVoteResponse>> updateVote(
      @PathVariable("studyId") Long studyId,
      @RequestBody @Valid TimeVoteUpdateRequest request,
      @AuthenticationPrincipal Principal userDetails
  ) {
    TimeVoteResponse response = timeVoteService.updateVotes(request, studyId,
        userDetails.getUserId());
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @DeleteMapping("/votes")
  public ResponseEntity<ApiResponse<Void>> deleteAllVotes(
      @PathVariable("studyId") Long studyId,
      @AuthenticationPrincipal Principal userDetails
  ) {
    timeVoteService.deleteAllVotes(studyId, userDetails.getUserId());
    return ResponseEntity.ok(ApiResponse.success("성공적으로 투표를 삭제했습니다."));
  }

  @GetMapping("/periods/submissions")
  public ResponseEntity<ApiResponse<List<TimeVoteSubmissionStatusResponse>>> getSubmissionStatusList(
      @PathVariable("studyId") Long studyId,
      @AuthenticationPrincipal Principal userDetails
  ) {
    List<TimeVoteSubmissionStatusResponse> statuses = timeVoteService.getSubmissionStatusList(
        studyId, userDetails.getUserId()
    );
    return ResponseEntity.ok(ApiResponse.success(statuses));
  }

  @GetMapping("/periods/stats")
  public ResponseEntity<ApiResponse<TimeVoteStatResponse>> getVoteStats(
      @PathVariable("studyId") Long studyId,
      @AuthenticationPrincipal Principal userDetails
  ) {
    TimeVoteStatResponse response = timeVoteStatService.getStats(studyId, userDetails.getUserId());
    return ResponseEntity.ok(ApiResponse.success(response));
  }
}
