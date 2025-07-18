package grep.neogul_coder.domain.admin.controller;

import grep.neogul_coder.domain.admin.controller.dto.response.AdminRecruitmentPostResponse;
import grep.neogul_coder.domain.admin.controller.dto.response.AdminStudyResponse;
import grep.neogul_coder.domain.admin.controller.dto.response.AdminUserResponse;
import grep.neogul_coder.domain.admin.service.AdminService;
import grep.neogul_coder.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public ApiResponse<Page<AdminUserResponse>> getUsers(@RequestParam(defaultValue = "0") int page) {
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.success(adminService.getAllUsers(pageable));
    }

    @GetMapping("/studies")
    public ApiResponse<Page<AdminStudyResponse>> getStudies(@RequestParam(defaultValue = "0") int page) {
        return ApiResponse.success(adminService.getAllStudies(page));
    }

    @GetMapping("/recruitment-posts")
    public ApiResponse<Page<AdminRecruitmentPostResponse>> getRecruitmentPosts(
        @RequestParam(defaultValue = "0") int page) {

        return ApiResponse.success(adminService.getAllRecruitmentPosts(page));
    }

    @DeleteMapping("/users/{userId}")
    public ApiResponse<Void> deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return ApiResponse.noContent();
    }

    @DeleteMapping("/studies/{studyId}")
    public ApiResponse<Void> deleteStudy(@PathVariable Long studyId) {
        adminService.deleteStudy(studyId);
        return ApiResponse.noContent();
    }

    @DeleteMapping("/studies/{postId}")
    public ApiResponse<Void> deleteRecruitmentPost(@PathVariable Long postId) {
        adminService.deleteRecruitmentPost(postId);
        return ApiResponse.noContent();
    }

}
