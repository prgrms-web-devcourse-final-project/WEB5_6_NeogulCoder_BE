package grep.neogul_coder.domain.admin.controller;

import grep.neogul_coder.domain.admin.controller.dto.response.AdminRecruitmentPostResponse;
import grep.neogul_coder.domain.admin.controller.dto.response.AdminStudyResponse;
import grep.neogul_coder.domain.admin.controller.dto.response.AdminUserResponse;
import grep.neogul_coder.domain.study.enums.Category;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "관리자 API", description = "관리자 기능 관련 API")
public interface AdminSpecification {

    @Operation(summary = "전체 사용자 조회", description = "관리자가 전체 유저 목록을 페이지 단위로 조회합니다.")
    @GetMapping("/admin/users")
    ApiResponse<Page<AdminUserResponse>> getUsers(
        @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
        @RequestParam(defaultValue = "0") int page, @RequestParam(required = false) String email
    );

    @Operation(summary = "전체 스터디 조회", description = "관리자가 전체 스터디 목록을 페이지 단위로 조회합니다.")
    @GetMapping("/admin/studies")
    ApiResponse<Page<AdminStudyResponse>> getStudies(
        @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Category category
    );

    @Operation(summary = "전체 모집글 조회", description = "관리자가 전체 모집글 목록을 페이지 단위로 조회합니다.")
    @GetMapping("/admin/recruitment-posts")
    ApiResponse<Page<AdminRecruitmentPostResponse>> getRecruitmentPosts(
        @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(required = false) String subject
    );

    @Operation(summary = "사용자 강제 탈퇴", description = "관리자가 특정 사용자를 탈퇴(삭제) 처리합니다.")
    @DeleteMapping("/admin/users/{userId}")
    ApiResponse<Void> deleteUser(
        @Parameter(description = "삭제할 사용자 ID", example = "1")
        @PathVariable Long userId
    );

    @Operation(summary = "스터디 비활성화", description = "관리자가 특정 스터디를 비활성화(삭제) 처리합니다.")
    @DeleteMapping("/admin/studies/{studyId}")
    ApiResponse<Void> deleteStudy(
        @Parameter(description = "삭제할 스터디 ID", example = "1")
        @PathVariable Long studyId
    );

    @Operation(summary = "모집글 삭제", description = "관리자가 특정 모집글을 삭제 처리합니다.")
    @DeleteMapping("/admin/recruitment-posts/{postId}")
    ApiResponse<Void> deleteRecruitmentPost(
        @Parameter(description = "삭제할 모집글 ID", example = "1")
        @PathVariable Long postId
    );

}
