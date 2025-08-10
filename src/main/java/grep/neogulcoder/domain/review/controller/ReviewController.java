package grep.neogulcoder.domain.review.controller;

import grep.neogulcoder.domain.review.controller.dto.request.ReviewSaveRequest;
import grep.neogulcoder.domain.review.controller.dto.response.MyReviewTagsInfo;
import grep.neogulcoder.domain.review.controller.dto.response.ReviewContentsPagingInfo;
import grep.neogulcoder.domain.review.controller.dto.response.ReviewTargetStudiesInfo;
import grep.neogulcoder.domain.review.controller.dto.response.ReviewTargetUsersInfo;
import grep.neogulcoder.domain.review.service.ReviewService;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RequestMapping("/reviews")
@RestController
public class ReviewController implements ReviewSpecification {

    private final ReviewService reviewService;

    @GetMapping("/studies/{study-id}/targets")
    public ResponseEntity<ApiResponse<ReviewTargetUsersInfo>> getReviewTargetUsersInfo(@PathVariable("study-id") long studyId,
                                                                                       @AuthenticationPrincipal Principal userDetails) {
        ReviewTargetUsersInfo response = reviewService.getReviewTargetUsersInfo(studyId, userDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/studies/me")
    public ResponseEntity<ApiResponse<ReviewTargetStudiesInfo>> getReviewTargetStudiesInfo(@AuthenticationPrincipal Principal userDetails) {
        ReviewTargetStudiesInfo response = reviewService.getReviewTargetStudiesInfo(userDetails.getUserId(), LocalDateTime.now());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> save(@Valid @RequestBody ReviewSaveRequest request, @AuthenticationPrincipal Principal userDetails) {
        reviewService.save(request.toServiceRequest(), userDetails.getUserId());
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    @GetMapping("/users/{user-id}/tags")
    public ResponseEntity<ApiResponse<MyReviewTagsInfo>> getMyReviewTags(@PathVariable("user-id") long userId) {
        MyReviewTagsInfo response = reviewService.getMyReviewTags(userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/users/{user-id}/contents")
    public ResponseEntity<ApiResponse<ReviewContentsPagingInfo>> getMyReviewContents(@PageableDefault(size = 4) Pageable pageable,
                                                                                     @PathVariable("user-id") long userId) {
        ReviewContentsPagingInfo response = reviewService.getMyReviewContents(pageable, userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
