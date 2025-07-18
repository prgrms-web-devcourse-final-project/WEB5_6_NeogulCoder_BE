package grep.neogul_coder.domain.recruitment.post.controller;

import grep.neogul_coder.domain.recruitment.post.controller.dto.request.RecruitmentPostStatusUpdateRequest;
import grep.neogul_coder.domain.recruitment.post.controller.dto.request.RecruitmentPostUpdateRequest;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.RecruitmentApplicationPagingInfo;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.RecruitmentPostCommentPagingInfo;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.RecruitmentPostInfo;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.RecruitmentPostPagingInfo;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;

@Tag(name = "Recruitment-Post", description = "모집 글 API")
public interface RecruitmentPostSpecification {

    @Operation(summary = "모집 글 조회", description = "모집 글의 상세 내역을 조회 합니다.")
    ApiResponse<RecruitmentPostInfo> get(long id);

    @Operation(summary = "모집 글 수정", description = "모집글을 수정 합니다.")
    ApiResponse<Long> update(long recruitmentPostId, RecruitmentPostUpdateRequest request, Principal userDetails);

    @Operation(summary = "모집 글 삭제", description = "모집글을 삭제 합니다.")
    ApiResponse<Void> delete(long recruitmentPostId, Principal userDetails);

    @Operation(summary = "모집 상태 변경", description = "모집글의 상태를 변경 합니다.")
    ApiResponse<Long> changeStatus(long recruitmentPostId, RecruitmentPostStatusUpdateRequest request, Principal userDetails);

    @Operation(
            summary = "모집글 페이징 조회",
            description = """
                    특정 조건에 따라 모집글 목록을 페이징하여 조회합니다.
                    
                    ✅ 요청 예시:  
                    `GET /recruitment-posts?page=0&size=5`
                    
                    ✅ 응답 예시:
                    ```json
                    {
                      "data": {
                        "postInfos": [
                          {
                            "subject": "자바 스터디 모집 합니다!",
                            "content": "자바 스터디는 주 3회 오후 6시에 진행 됩니다.",
                            "category": "IT",
                            "studyType": "온라인",
                            "status": "모집중",
                            "commentCount": 3,
                            "createAt": "2025-07-15"
                          },
                          {
                            "subject": "이펙티브 자바 함께 읽어요",
                            "content": "이펙티브 자바 3판을 함께 읽으며 토론하는 스터디입니다.",
                            "category": "개발",
                            "studyType": "오프라인",
                            "status": "모집완료",
                            "commentCount": 5,
                            "createAt": "2025-07-10"
                          }
                        ]
                      }
                    }
                    ```
                    """
    )
    ApiResponse<RecruitmentPostPagingInfo> getPagingInfo(Pageable pageable);

    @Operation(
            summary = "스터디 신청한 회원 목록 페이징 조회",
            description = """
                    특정 모집글에 신청한 회원 목록을 페이징하여 조회합니다.
                    
                    ✅ 요청 형식:
                    `GET /recruitment-posts/{id}/applications?page=0&size=5`
                    
                    ✅ 응답 예시:
                    ```json
                    {
                      "applicationInfos": [
                        {
                          "nickname": "테스터",
                          "buddyEnergy": 30,
                          "createdDate": "2025-07-13T15:30:00",
                          "applicationReason": "자바를 더 공부 하고싶어요!"
                        }
                      ],
                      "totalPage": 3,
                      "totalElementCount": 20
                    }
                    ```                
                    """
    )
    ApiResponse<RecruitmentApplicationPagingInfo> getApplications(Pageable pageable, long recruitmentPostId);

    @Operation(
            summary = "모집글 댓글 페이징 조회",
            description = """
                    특정 모집글에 작성된 댓글들을 페이징하여 조회합니다.
                    
                    ✅ 요청 형식:
                    `GET /recruitment-posts/{id}/comments?page=0&size=5`
                    
                    ✅ 응답 예시:
                    ```json
                    {
                      "commentsInfos": [
                        {
                          "nickname": "테스터",
                          "imageUrl": "www.s3.com",
                          "content": "참여 하고 싶습니다!"
                        },
                        {
                          "nickname": "철수",
                          "imageUrl": "www.s3.com/철수.png",
                          "content": "스터디에 관심 있습니다!"
                        }
                      ],
                      "totalPage": 3,
                      "totalElementCount": 20
                    }
                    ```    
                    """
    )
    ApiResponse<RecruitmentPostCommentPagingInfo> getComments(Pageable pageable, long recruitmentPostId);
}
