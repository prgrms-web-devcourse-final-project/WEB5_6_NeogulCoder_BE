package grep.neogul_coder.domain.review.controller;

import grep.neogul_coder.domain.review.controller.dto.request.ReviewSaveRequest;
import grep.neogul_coder.domain.review.controller.dto.response.ReviewPagingContentsInfo;
import grep.neogul_coder.domain.review.controller.dto.response.ReviewTagsInfo;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/reviews")
@RestController
public class ReviewController implements ReviewSpecification {

    @PostMapping
    public ApiResponse<Void> save(@RequestBody ReviewSaveRequest request, @AuthenticationPrincipal Principal userDetails) {
        return ApiResponse.noContent("리뷰 생성 성공");
    }

    @GetMapping("/tags")
    public ApiResponse<ReviewTagsInfo> getReviewTags(@AuthenticationPrincipal Principal userDetails) {
        return ApiResponse.success(new ReviewTagsInfo());
    }

    @GetMapping("/contents")
    public ApiResponse<ReviewPagingContentsInfo> getReviewContents(@AuthenticationPrincipal Principal userDetails) {
        return ApiResponse.success(new ReviewPagingContentsInfo());
    }
}
