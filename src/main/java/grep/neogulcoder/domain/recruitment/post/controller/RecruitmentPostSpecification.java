package grep.neogulcoder.domain.recruitment.post.controller;

import grep.neogulcoder.domain.recruitment.post.controller.dto.request.RecruitmentPostStatusUpdateRequest;
import grep.neogulcoder.domain.recruitment.post.controller.dto.request.RecruitmentPostUpdateRequest;
import grep.neogulcoder.domain.recruitment.post.controller.dto.response.RecruitmentPostInfo;
import grep.neogulcoder.domain.recruitment.post.controller.dto.response.RecruitmentPostPagingInfo;
import grep.neogulcoder.domain.study.enums.Category;
import grep.neogulcoder.domain.study.enums.StudyType;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Recruitment-Post", description = "모집 글 API")
public interface RecruitmentPostSpecification {

    @Operation(summary = "모집 글 조회", description = "모집 글의 상세 내역을 조회 합니다.")
    ResponseEntity<ApiResponse<RecruitmentPostInfo>> get(long id);

    @Operation(summary = "모집 글 수정", description = "모집글을 수정 합니다.")
    ResponseEntity<ApiResponse<Long>> update(long recruitmentPostId, RecruitmentPostUpdateRequest request, Principal userDetails);

    @Operation(summary = "모집 글 삭제", description = "모집글을 삭제 합니다.")
    ResponseEntity<ApiResponse<Void>> delete(long recruitmentPostId, Principal userDetails);

    @Operation(summary = "모집 상태 변경", description = "모집글의 상태를 변경 합니다.")
    ResponseEntity<ApiResponse<Long>> changeStatus(long recruitmentPostId, RecruitmentPostStatusUpdateRequest request, Principal userDetails);

    @Operation(
            summary = "내가 등록한 모집글 페이징 조회",
            description = """
                    내가 등록한 모집글 목록을 페이징하여 조회합니다.
                    
                    ✅ 요청 예시:  
                    `GET /recruitment-posts/me?page=0&size=5`
                    
                    ✅ studyType (스터디 방식): \s
                            - `ONLINE("온라인")` \s
                            - `OFFLINE("오프라인")` \s
                            - `HYBRID("병행")`
                    
                    ✅ category (스터디 카테고리): \s
                            - `LANGUAGE("어학")` \s
                            - `IT("IT")` \s
                            - `EXAM("고시/자격증")` \s
                            - `FINANCE("금융")` \s
                            - `MANAGEMENT("경영")` \s
                            - `DESIGN("디자인")` \s
                            - `ART("예술")` \s
                            - `PHOTO_VIDEO("사진/영상")` \s
                            - `BEAUTY("뷰티")` \s
                            - `SPORTS("스포츠")` \s
                            - `HOBBY("취미")` \s
                            - `ETC("기타")`
                    
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
    ResponseEntity<ApiResponse<RecruitmentPostPagingInfo>> searchMyRecruitmentPost(Pageable pageable, Category category, StudyType studyType,
                                                                                   String keyword, Principal userDetails);

    @Operation(
            summary = "모집글 페이징 조회",
            description = """
                    특정 조건에 따라 모집글 목록을 페이징하여 조회합니다.
                    
                    ✅ 요청 예시:  
                    `GET /recruitment-posts?page=0&size=5`
                    
                    ✅ studyType (스터디 방식): \s
                            - `ONLINE("온라인")` \s
                            - `OFFLINE("오프라인")` \s
                            - `HYBRID("병행")`
                    
                    ✅ category (스터디 카테고리): \s
                            - `LANGUAGE("어학")` \s
                            - `IT("IT")` \s
                            - `EXAM("고시/자격증")` \s
                            - `FINANCE("금융")` \s
                            - `MANAGEMENT("경영")` \s
                            - `DESIGN("디자인")` \s
                            - `ART("예술")` \s
                            - `PHOTO_VIDEO("사진/영상")` \s
                            - `BEAUTY("뷰티")` \s
                            - `SPORTS("스포츠")` \s
                            - `HOBBY("취미")` \s
                            - `ETC("기타")`
                    
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
    ResponseEntity<ApiResponse<RecruitmentPostPagingInfo>> search(Pageable pageable, Category category, StudyType studyType, String keyword);
}
