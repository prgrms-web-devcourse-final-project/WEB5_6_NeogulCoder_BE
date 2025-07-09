package grep.neogul_coder.domain.review.controller;

import grep.neogul_coder.domain.review.controller.dto.request.ReviewSaveRequest;
import grep.neogul_coder.domain.review.controller.dto.response.ReviewTagsInfo;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "review", description = "리뷰 API")
public interface ReviewSpecification {

    @Operation(summary = "리뷰 생성", description = "스터디에 대한 리뷰를 작성 합니다.")
    ApiResponse<Void> save(ReviewSaveRequest request, Long studyId, Principal userDetails);

    @Operation(summary = "리뷰 태그 조회", description = "회원이 받은 리뷰 태그의 종류와 수를 조회 합니다.")
    ApiResponse<ReviewTagsInfo> getReviewTags(Principal userDetails);
}
