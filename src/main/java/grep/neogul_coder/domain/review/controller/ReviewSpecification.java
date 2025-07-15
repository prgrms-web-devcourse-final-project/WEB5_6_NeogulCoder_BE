package grep.neogul_coder.domain.review.controller;

import grep.neogul_coder.domain.review.controller.dto.request.ReviewSaveRequest;
import grep.neogul_coder.domain.review.controller.dto.response.MyReviewTagsInfo;
import grep.neogul_coder.domain.review.controller.dto.response.ReviewContentsPagingInfo;
import grep.neogul_coder.domain.review.controller.dto.response.ReviewTargetUsersInfo;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;

@Tag(name = "Review", description = "리뷰 API")
public interface ReviewSpecification {

    @Operation(
            summary = "리뷰 대상 회원들 조회",
            description = """
                    리뷰를 작성할 대상 회원들과 해당 스터디 정보를 조회합니다.
                    
                    ✅ 응답 형식 (예시):
                    ```json
                    {
                      "userInfos": [
                        { "userId": 2, "nickname": "짱구" },
                        { "userId": 3, "nickname": "철수" }
                      ],
                      
                      "studyInfo": {
                        "studyId" 3,
                        "studyName": "자바 스터디",
                        "imageUrl": "www.s3.com"
                      }
                    }
                    ```
                    
                    - `userInfos`: 리뷰를 작성할 대상 회원들의 닉네임 리스트입니다.
                    
                    - `studyInfo`: 리뷰가 작성될 스터디의 이름과 이미지 정보입니다.
                    """
    )
    ApiResponse<ReviewTargetUsersInfo> getReviewTargetUsersInfo(long studyId, Principal userDetails);

    @Operation(summary = "리뷰 생성", description = "스터디에 대한 리뷰를 작성 합니다.")
    ApiResponse<Void> save(ReviewSaveRequest request, Principal userDetails);

    @Operation(
            summary = "자신이 받은 리뷰 태그 조회",
            description = """
                    회원이 받은 리뷰 태그의 종류와 개수를 조회합니다.
                    
                    ✅ 응답 형식 (예시):
                    ```json
                    {
                      "GOOD": [
                        { "reviewTag": "시간을 잘 지켜요", "reviewTagCount": 3 },
                        { "reviewTag": "친절해요", "reviewTagCount": 2 }
                      ],
                      
                      "BAD": [
                        { "reviewTag": "지각했어요", "reviewTagCount": 2 }
                      ]
                    }
                    ```
                    
                    - 키(`GOOD`, `BAD`)는 리뷰 유형(ReviewType)을 의미합니다.
                    
                    - 값은 해당 유형에 속한 태그별 사용 횟수를 나타냅니다.
                    
                    - 내부 반환 값은 Map<String, List<ReviewTagCountInfo>> 입니다.
                    """
    )
    ApiResponse<MyReviewTagsInfo> getMyReviewTags(Principal userDetails);

    @Operation(summary = "자신이 받은 주관 리뷰들 조회",
            description = """
                    회원이 받은 **주관식 리뷰**를 페이징하여 조회합니다.
                    
                    📌 페이징 조건:
                    - `page` : 조회할 페이지 번호 (0부터 시작)
                    
                    - `size` : 한 페이지에 포함할 리뷰 수
                    
                    ✅ 예시:
                    `/reviews/me/contents?page=0&size=5`
                    """)
    ApiResponse<ReviewContentsPagingInfo> getMyReviewContents(Pageable pageable, Principal userDetails);
}
