package grep.neogul_coder.domain.recruitment.post.controller;

import grep.neogul_coder.domain.recruitment.post.controller.dto.request.save.RecruitmentPostCreateRequest;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.save.JoinedStudyLoadInfo;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.save.JoinedStudiesInfo;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Recruitment-Post-Save", description = "모집글 저장 API")
public interface RecruitmentPostSaveSpecification {

    @Operation(summary = "모집글 저장", description = "스터디 모집 글을 저장 합니다.")
    ApiResponse<Long> save(RecruitmentPostCreateRequest request, Principal userDetails);

    @Operation(
            summary = "참여중인 스터디 목록 조회",
            description = """
                    회원이 참여 중인 스터디들의 이름과 식별자(ID) 목록을 조회합니다.  
                    모집글 작성 시, 스터디 불러오기를 위해 활용됩니다.
                    
                    ✅ 응답 예시:
                    ```json
                    [
                      { "studyId": 1, "name": "자바 스터디" },
                      
                      { "studyId": 2, "name": "면접 스터디" }
                    ]
                    ```
                    
                    - `studyId`: 스터디의 고유 식별자
                    
                    - `name`: 스터디 이름
                    """
    )
    ApiResponse<JoinedStudiesInfo> getJoinedStudyInfo(Principal userDetails);

    @Operation(
            summary = "참여 중인 스터디 정보 불러오기",
            description = """
                    스터디 모집글 작성시, 참여 중인 스터디를 선택하여  
                    해당 스터디의 상세 정보를 불러올 수 있습니다.
                    
                    ✅ 응답 예시:
                    ```json
                    {
                      "category": "IT",
                      "location": "경주 경리단길",
                      "studyType": "ONLINE",
                      "startDate": "2025-07-13",
                      "endDate": "2025-07-14",
                      "remainSlots": 3
                    }
                    ```
                    
                    - `remainSlots`: 스터디 정원 - 스터디 참여 인원 
                    """
    )
    ApiResponse<JoinedStudyLoadInfo> getJoinedStudyLoadInfo(long studyId, Principal userDetails);
}
