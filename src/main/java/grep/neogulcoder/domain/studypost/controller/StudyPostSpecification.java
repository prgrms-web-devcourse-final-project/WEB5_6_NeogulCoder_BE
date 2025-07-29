package grep.neogulcoder.domain.studypost.controller;

import grep.neogulcoder.domain.studypost.Category;
import grep.neogulcoder.domain.studypost.controller.dto.request.StudyPostSaveRequest;
import grep.neogulcoder.domain.studypost.controller.dto.request.StudyPostUpdateRequest;
import grep.neogulcoder.domain.studypost.controller.dto.response.MyStudyPostPagingResult;
import grep.neogulcoder.domain.studypost.controller.dto.response.PostPagingResult;
import grep.neogulcoder.domain.studypost.controller.dto.response.StudyPostDetailResponse;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Study-Post", description = "스터디 게시판 API")
public interface StudyPostSpecification {

    @Operation(summary = "내 게시글 검색 검색 페이징 조회",  description = """
                    내 스터디의 게시글을 조건에 따라 페이징하여 조회합니다.
                    
                    ✅ 요청 예시:
                    `GET /api/studies/{study-id}/posts/search/me?page=0&size=2&sort=createDateTime,DESC&category=NOTICE&keyword=검색
                    
                    ✅ query 설명:
                    - `page`: 조회할 페이지 번호 (0부터 시작)
                    
                    - `pageSize`: 한 페이지에 표시할 게시글 수
                    
                    - `category`: 게시글 카테고리 (예: NOTICE, FREE)
                    
                    - `keyword`: 게시글 내용 검색 키워드
                    
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
                    """)
    ResponseEntity<ApiResponse<MyStudyPostPagingResult>> getMyPostSearch(long studyId, Pageable pageable, Category category, String keyword, Principal userDetails);

    @Operation(summary = "게시글 생성", description = "스터디에 새로운 게시글을 작성합니다.")
    ResponseEntity<ApiResponse<Long>> create(long studyId, StudyPostSaveRequest request, Principal userDetails);

    @Operation(
            summary = "게시글 검색 페이징 조회",
            description = """
                    스터디의 게시글을 조건에 따라 페이징하여 조회합니다.
                    
                    ✅ 요청 예시:
                    `GET /api/studies/{study-id}/posts/search?page=0&size=2&sort=createDateTime,DESC&category=NOTICE&keyword=검색
                    
                    ✅ query 설명:
                    - `page`: 조회할 페이지 번호 (0부터 시작)
                    
                    - `pageSize`: 한 페이지에 표시할 게시글 수
                    
                    - `category`: 게시글 카테고리 (예: NOTICE, FREE)
                    
                    - `keyword`: 게시글 내용 검색 키워드
                    
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
    ResponseEntity<ApiResponse<PostPagingResult>> findPagingInfo(Long studyId, Pageable pageable, Category category, String keyword);

    @Operation(
            summary = "게시글 상세 조회",
            description = """
                    특정 게시글의 상세 정보를 조회합니다.
                    
                    ✅ 요청 예시:
                    `GET /api/studies/posts/{post-id}`
                    
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
    ResponseEntity<ApiResponse<StudyPostDetailResponse>> findOne(
            @Parameter(description = "게시글 ID", example = "15") Long postId
    );

    @Operation(summary = "게시글 수정", description = "기존 게시글을 수정합니다.")
    ResponseEntity<ApiResponse<Void>> update(
            @Parameter(description = "게시글 ID", example = "15") Long postId,
            StudyPostUpdateRequest request,
            Principal userDetails
    );

    @Operation(summary = "게시글 삭제", description = "특정 게시글을 삭제합니다.")
    ResponseEntity<ApiResponse<Void>> delete(@Parameter(description = "게시글 ID", example = "15") Long postId,
                             @AuthenticationPrincipal Principal userDetails);

    @Operation(summary = "스터디 게시글 이미지 등록", description = "게시글에 이미지를 등록 합니다.")
    ResponseEntity<ApiResponse<String>> uploadPostImage(MultipartFile file, Principal userDetails) throws IOException;
}
