package grep.neogul_coder.domain.studypost.controller;

import grep.neogul_coder.domain.studypost.controller.dto.request.StudyPostPagingCondition;
import grep.neogul_coder.domain.studypost.controller.dto.request.StudyPostSaveRequest;
import grep.neogul_coder.domain.studypost.controller.dto.request.StudyPostUpdateRequest;
import grep.neogul_coder.domain.studypost.controller.dto.response.PostPagingResult;
import grep.neogul_coder.domain.studypost.controller.dto.response.StudyPostDetailResponse;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Tag(name = "Study-Post", description = "스터디 게시판 API")
public interface StudyPostSpecification {

    @Operation(summary = "게시글 생성", description = "스터디에 새로운 게시글을 작성합니다.")
    ApiResponse<Long> create(StudyPostSaveRequest request, Principal userDetails);

    @Operation(
            summary = "게시글 목록 페이징 조회",
            description = """
                    스터디의 게시글을 조건에 따라 페이징하여 조회합니다.
                    
                    ✅ 요청 예시:
                    `GET /api/posts/studies/{study-id}
                    
                    ✅ condition 설명:
                    - `page`: 조회할 페이지 번호 (0부터 시작)
                    
                    - `pageSize`: 한 페이지에 표시할 게시글 수
                    
                    - `category`: 게시글 카테고리 (예: NOTICE, FREE)
                    
                    - `content`: 게시글 내용 검색 키워드
                    
                    - `attributeName`: 정렬 대상 속성 (예: commentCount, createDateTime)
                    
                    - `sort`: 정렬 방향 (ASC 또는 DESC)
                    
                    ✅ 응답 예시:
                    ```json
                    {
                      "data": {
                        "noticePostInfos": [
                          {
                            "postId": 3,
                            "category": "공지",
                            "title": "스터디 일정 공지",
                            "createdAt": "2025-07-21"
                          }
                        ],
                        "postInfos": [
                          {
                            "id": 12,
                            "title": "모든 국민은 직업선택의 자유를 가진다.",
                            "category": "NOTICE",
                            "content": "국회는 의원의 자격을 심사하며, 의원을 징계할 있다.",
                            "createdDate": "2025-07-10T14:32:00",
                            "commentCount": 3
                          }
                        ],
                        "totalPage": 3,
                        "totalElementCount": 12,
                        "hasNext": true
                      }
                    }
                    ```
                    """
    )
    ApiResponse<PostPagingResult> findPagingInfo(@Parameter(description = "스터디 ID", example = "1") Long studyId,
                                                 StudyPostPagingCondition condition);

    @Operation(
            summary = "게시글 상세 조회",
            description = """
            특정 게시글의 상세 정보를 조회합니다.

            ✅ 요청 예시:
            `GET /api/posts/{post-id}`

            ✅ 응답 예시:
            ```json
            {
              "data": {
                "postInfo": {
                  "postId": 15,
                  "title": "스터디에 참여해주세요",
                  "category": "NOTICE",
                  "content": "매주 월요일 정기모임 진행합니다.",
                  "createdDate": "2025-07-21T15:32:00",
                  "commentCount": 3
                },
                "comments": [
                  {
                    "userId": 3,
                    "nickname": "너굴코더",
                    "profileImageUrl": "https://cdn.example.com/profile.jpg",
                    "id": 100,
                    "content": "정말 좋은 정보 감사합니다!",
                    "createdAt": "2025-07-10T14:45:00"
                  },
                  {
                    "userId": 4,
                    "nickname": "코딩곰",
                    "profileImageUrl": "https://cdn.example.com/codingbear.png",
                    "id": 101,
                    "content": "참석하겠습니다!",
                    "createdAt": "2025-07-10T15:12:00"
                  }
                ],
                "commentCount": 3
              }
            }
            ```
            """
    )
    ApiResponse<StudyPostDetailResponse> findOne(
            @Parameter(description = "게시글 ID", example = "15") Long postId
    );

    @Operation(summary = "게시글 수정", description = "기존 게시글을 수정합니다.")
    ApiResponse<Void> update(
            @Parameter(description = "게시글 ID", example = "15") Long postId,
            StudyPostUpdateRequest request,
            Principal userDetails
    );

    @Operation(summary = "게시글 삭제", description = "특정 게시글을 삭제합니다.")
    ApiResponse<Void> delete(@Parameter(description = "게시글 ID", example = "15") Long postId,
                             @AuthenticationPrincipal Principal userDetails);
}
