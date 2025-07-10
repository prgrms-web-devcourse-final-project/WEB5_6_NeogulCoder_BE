package grep.neogul_coder.domain.review.controller;

import grep.neogul_coder.domain.review.controller.dto.request.ReviewSaveRequest;
import grep.neogul_coder.domain.review.controller.dto.response.ReviewTagsInfo;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController implements ReviewSpecification {

    @PostMapping("/studies/{study-id}/reviews/new")
    public ApiResponse<Void> save(ReviewSaveRequest request,
                                  @PathVariable("study-id") Long studyId, @AuthenticationPrincipal Principal userDetails) {
        return ApiResponse.noContent();
    }

    @GetMapping("/review-tags")
    public ApiResponse<ReviewTagsInfo> getReviewTags(@AuthenticationPrincipal Principal userDetails) {
        ReviewTagsInfo response = new ReviewTagsInfo();
        return ApiResponse.success(response);
    }
}
