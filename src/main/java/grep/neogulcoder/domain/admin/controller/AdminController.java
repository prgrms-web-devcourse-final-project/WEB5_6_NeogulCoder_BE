package grep.neogulcoder.domain.admin.controller;

import grep.neogulcoder.domain.admin.controller.dto.response.AdminRecruitmentPostResponse;
import grep.neogulcoder.domain.admin.controller.dto.response.AdminStudyResponse;
import grep.neogulcoder.domain.admin.controller.dto.response.AdminUserResponse;
import grep.neogulcoder.domain.admin.service.AdminService;
import grep.neogulcoder.domain.study.enums.Category;
import grep.neogulcoder.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
public class AdminController implements AdminSpecification {

    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<Page<AdminUserResponse>>> getUsers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(required = false) String email) {
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.success(adminService.getAllUsers(pageable, email)));
    }

    @GetMapping("/studies")
    public ResponseEntity<ApiResponse<Page<AdminStudyResponse>>> getStudies(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Category category) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getAllStudies(page,name,category)));
    }

    @GetMapping("/recruitment-posts")
    public ResponseEntity<ApiResponse<Page<AdminRecruitmentPostResponse>>> getRecruitmentPosts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(required = false) String subject) {

        return ResponseEntity.ok(ApiResponse.success(adminService.getAllRecruitmentPosts(page, subject)));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    @DeleteMapping("/studies/{studyId}")
    public ResponseEntity<ApiResponse<Void>> deleteStudy(@PathVariable Long studyId) {
        adminService.deleteStudy(studyId);
        return ResponseEntity.ok(ApiResponse.noContent());
    }

    @DeleteMapping("/recruitment-posts/{postId}")
    public ResponseEntity<ApiResponse<Void>> deleteRecruitmentPost(@PathVariable Long postId) {
        adminService.deleteRecruitmentPost(postId);
        return ResponseEntity.ok(ApiResponse.noContent());
    }

}
