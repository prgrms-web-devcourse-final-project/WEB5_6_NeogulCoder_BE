package grep.neogul_coder.domain.recruitment.post.controller;

import grep.neogul_coder.domain.recruitment.post.controller.dto.request.RecruitmentPostEditRequest;
import grep.neogul_coder.domain.recruitment.post.controller.dto.request.RecruitmentPostSaveRequest;
import grep.neogul_coder.domain.recruitment.post.controller.dto.request.RecruitmentPostStatusEditRequest;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.RecruitmentPostInfo;
import grep.neogul_coder.domain.studyapplication.controller.dto.response.ApplicationResponse;
import grep.neogul_coder.global.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/recruitment-posts")
@RestController
public class RecruitmentPostController implements RecruitmentPostSpecification {

    @GetMapping("/{recruitment-post-id}")
    public ApiResponse<RecruitmentPostInfo> get(@PathVariable("recruitment-post-id") long recruitmentPostId) {
        return ApiResponse.success(new RecruitmentPostInfo());
    }

    @PostMapping
    public ApiResponse<Void> save(@RequestBody RecruitmentPostSaveRequest request) {
        return ApiResponse.noContent();
    }

    @PutMapping("/{recruitment-post-id}")
    public ApiResponse<Void> edit(@PathVariable("recruitment-post-id") long recruitmentPostId,
                                  RecruitmentPostEditRequest request) {
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{recruitment-post-id}")
    public ApiResponse<Void> delete(@PathVariable("recruitment-post-id") long recruitmentPostId) {
        return ApiResponse.noContent();
    }

    @PostMapping("/{recruitment-post-id}/status")
    public ApiResponse<Void> changeStatus(@PathVariable("recruitment-post-id") long recruitmentPostId,
                                          @RequestBody RecruitmentPostStatusEditRequest request) {
        return ApiResponse.noContent();
    }

    @GetMapping("{recruitment-post-id}/applications")
    public ApiResponse<List<ApplicationResponse>> getApplications(@PathVariable("recruitment-post-id") long recruitmentPostId) {
        return ApiResponse.success(List.of(new ApplicationResponse()));
    }
}
