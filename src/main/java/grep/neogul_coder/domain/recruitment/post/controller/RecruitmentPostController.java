package grep.neogul_coder.domain.recruitment.post.controller;

import grep.neogul_coder.domain.recruitment.post.controller.dto.request.RecruitmentPostEditRequest;
import grep.neogul_coder.domain.recruitment.post.controller.dto.request.RecruitmentPostSaveRequest;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.RecruitmentPostInfo;
import grep.neogul_coder.global.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class RecruitmentPostController implements RecruitmentPostSpecification {

    @GetMapping("/recruitment-posts/{recruitment-post-id}")
    public ApiResponse<RecruitmentPostInfo> get(@PathVariable("recruitment-post-id") long recruitmentPostId) {
        return ApiResponse.success(new RecruitmentPostInfo());
    }

    @PostMapping("/studies/{study-id}")
    public ApiResponse<Void> save(@PathVariable("study-id") Long studyId,
                                  @RequestBody RecruitmentPostSaveRequest request) {
        return ApiResponse.noContent();
    }

    @PutMapping("/recruitment-posts/{recruitment-post-id}")
    public ApiResponse<Void> edit(@PathVariable("recruitment-post-id") Long recruitmentPostId,
                                  RecruitmentPostEditRequest request) {
        return ApiResponse.noContent();
    }

    @DeleteMapping("/recruitment-posts/{recruitment-post-id}")
    public ApiResponse<Void> delete(@PathVariable("recruitment-post-id") Long recruitmentPostId) {
        return ApiResponse.noContent();
    }
}
