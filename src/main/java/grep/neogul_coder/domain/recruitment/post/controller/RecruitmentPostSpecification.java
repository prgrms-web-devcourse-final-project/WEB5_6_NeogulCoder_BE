package grep.neogul_coder.domain.recruitment.post.controller;

import grep.neogul_coder.domain.recruitment.post.controller.dto.request.RecruitmentPostCreateRequest;
import grep.neogul_coder.domain.recruitment.post.controller.dto.request.RecruitmentPostStatusUpdateRequest;
import grep.neogul_coder.domain.recruitment.post.controller.dto.request.RecruitmentPostUpdateRequest;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.*;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;

@Tag(name = "Recruitment-Post", description = "모집 글 API")
public interface RecruitmentPostSpecification {

    @Operation(summary = "모집 글 조회", description = "모집 글의 상세 내역을 조회 합니다.")
    ApiResponse<RecruitmentPostInfo> get(long id);

    @Operation(summary = "모집 글 저장", description = "스터디 모집 글을 저장 합니다.")
    ApiResponse<Void> save(RecruitmentPostCreateRequest request, Principal userDetails);

    @Operation(summary = "참여 중인 스터디 이름들 조회", description = "스터디 모집글 작성시 내가 참여한 스터디 이름들을 가져올수 있습니다.")
    ApiResponse<ParticipatedStudyNamesInfo> getParticipatedStudyNames(Principal userDetails);

    @Operation(summary = "스터디 불러오기", description = "스터디 모집글 작성시 스터디 불러오기를 통해 스터디 정보를 조회할 수 있습니다.")
    ApiResponse<ParticipatedStudyInfo> getParticipatedStudy(long studyId, Principal userDetails);

    @Operation(summary = "모집 글 수정", description = "모집글을 수정 합니다.")
    ApiResponse<Void> update(long recruitmentPostId, RecruitmentPostUpdateRequest request, Principal userDetails);

    @Operation(summary = "모집 글 삭제", description = "모집글을 삭제 합니다.")
    ApiResponse<Void> delete(long recruitmentPostId, Principal userDetails);

    @Operation(summary = "모집 상태 변경", description = "모집글의 상태를 변경 합니다.")
    ApiResponse<Void> changeStatus(long recruitmentPostId, RecruitmentPostStatusUpdateRequest request, Principal userDetails);

    @Operation(summary = "스터디 신청 목록 페이징 조회", description =
            "스터디 신청 목록들을 페이징 조회합니다. <br>" +
                    "사용법 : /recruitment-post/{id}/applications?page=0&size=5")
    ApiResponse<RecruitmentApplicationPagingInfo> getApplications(Pageable pageable, long recruitmentPostId);

    @Operation(summary = "모집글 댓글 페이징 조회", description = "모집글의 댓글들을 페이징 조회 합니다. <br>" +
            "사용법 : /recruitment-post/{id}/comments?page=0&size=5")
    ApiResponse<RecruitmentPostCommentPagingInfo> getComments(Pageable pageable, long recruitmentPostId);
}
