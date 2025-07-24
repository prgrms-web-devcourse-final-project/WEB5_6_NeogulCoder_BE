package grep.neogulcoder.domain.review.controller;

import grep.neogulcoder.domain.review.controller.dto.request.ReviewSaveRequest;
import grep.neogulcoder.domain.review.controller.dto.response.JoinedStudiesInfo;
import grep.neogulcoder.domain.review.controller.dto.response.MyReviewTagsInfo;
import grep.neogulcoder.domain.review.controller.dto.response.ReviewContentsPagingInfo;
import grep.neogulcoder.domain.review.controller.dto.response.ReviewTargetUsersInfo;
import grep.neogulcoder.domain.review.service.ReviewService;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/reviews")
@RestController
public class ReviewController implements ReviewSpecification {

    private final ReviewService reviewService;

    @GetMapping("/studies/{study-id}/targets")
    public ApiResponse<ReviewTargetUsersInfo> getReviewTargetUsersInfo(@PathVariable("study-id") long studyId,
                                                                       @AuthenticationPrincipal Principal userDetails) {
        ReviewTargetUsersInfo response = reviewService.getReviewTargetUsersInfo(studyId, userDetails.getUsername());
        return ApiResponse.success(response);
    }

    @GetMapping("/studies/me")
    public ApiResponse<JoinedStudiesInfo> getJoinedStudiesInfo(@AuthenticationPrincipal Principal userDetails) {
        JoinedStudiesInfo response = reviewService.getJoinedStudiesInfo(userDetails.getUserId());
        return ApiResponse.success(response);
    }

    @PostMapping
    public ApiResponse<Long> save(@Valid @RequestBody ReviewSaveRequest request, @AuthenticationPrincipal Principal userDetails) {
        reviewService.save(request.toServiceRequest(), userDetails.getUserId());
        return ApiResponse.noContent();
    }

    @GetMapping("/me/tags")
    public ApiResponse<MyReviewTagsInfo> getMyReviewTags(@AuthenticationPrincipal Principal userDetails) {
        MyReviewTagsInfo response = reviewService.getMyReviewTags(userDetails.getUserId());
        return ApiResponse.success(response);
    }

    @GetMapping("/me/contents")
    public ApiResponse<ReviewContentsPagingInfo> getMyReviewContents(@PageableDefault(size = 4) Pageable pageable,
                                                                     @AuthenticationPrincipal Principal userDetails) {
        ReviewContentsPagingInfo response = reviewService.getMyReviewContents(pageable, userDetails.getUserId());
        return ApiResponse.success(response);
    }
}
