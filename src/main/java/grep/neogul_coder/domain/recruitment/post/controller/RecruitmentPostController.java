package grep.neogul_coder.domain.recruitment.post.controller;

import grep.neogul_coder.domain.recruitment.post.controller.dto.request.RecruitmentPostCreateRequest;
import grep.neogul_coder.domain.recruitment.post.controller.dto.request.RecruitmentPostStatusUpdateRequest;
import grep.neogul_coder.domain.recruitment.post.controller.dto.request.RecruitmentPostUpdateRequest;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.ParticipatedStudyInfo;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.ParticipatedStudyNamesInfo;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.RecruitmentPostInfo;
import grep.neogul_coder.domain.recruitment.post.service.RecruitmentPostService;
import grep.neogul_coder.domain.studyapplication.controller.dto.response.ApplicationResponse;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/recruitment-posts")
@RequiredArgsConstructor
@RestController
public class RecruitmentPostController implements RecruitmentPostSpecification {

    private final RecruitmentPostService recruitmentPostService;

    @GetMapping("/{recruitment-post-id}")
    public ApiResponse<RecruitmentPostInfo> get(@PathVariable("recruitment-post-id") long recruitmentPostId) {
        return ApiResponse.success(new RecruitmentPostInfo());
    }

    @GetMapping("/studies")
    public ApiResponse<ParticipatedStudyNamesInfo> getParticipatedStudyNames(@AuthenticationPrincipal Principal userDetails) {
        return ApiResponse.success(new ParticipatedStudyNamesInfo());
    }

    @GetMapping("/studies/{study-id}")
    public ApiResponse<ParticipatedStudyInfo> getParticipatedStudy(@PathVariable("study-id") long StudyId,
                                                                   @AuthenticationPrincipal Principal userDetails) {
        return ApiResponse.success(new ParticipatedStudyInfo());
    }

    @PostMapping
    public ApiResponse<Void> save(@Valid @RequestBody RecruitmentPostCreateRequest request,
                                  @AuthenticationPrincipal Principal userDetails) {
        recruitmentPostService.create(request.toServiceRequest(), userDetails.getUserId());
        return ApiResponse.noContent();
    }

    @PutMapping("/{recruitment-post-id}")
    public ApiResponse<Void> update(@PathVariable("recruitment-post-id") long recruitmentPostId,
                                    @Valid @RequestBody RecruitmentPostUpdateRequest request,
                                    @AuthenticationPrincipal Principal userDetails) {
        recruitmentPostService.update(request.toServiceRequest(), recruitmentPostId, userDetails.getUserId());
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{recruitment-post-id}")
    public ApiResponse<Void> delete(@PathVariable("recruitment-post-id") long recruitmentPostId,
                                    @AuthenticationPrincipal Principal userDetails) {
        recruitmentPostService.delete(recruitmentPostId, userDetails.getUserId());
        return ApiResponse.noContent();
    }

    @PostMapping("/{recruitment-post-id}/status")
    public ApiResponse<Void> changeStatus(@PathVariable("recruitment-post-id") long recruitmentPostId,
                                          @RequestBody RecruitmentPostStatusUpdateRequest request,
                                          @AuthenticationPrincipal Principal userDetails) {
        recruitmentPostService.updateStatus(request.toServiceRequest(), recruitmentPostId, userDetails.getUserId());
        return ApiResponse.noContent();
    }

    @GetMapping("{recruitment-post-id}/applications")
    public ApiResponse<List<ApplicationResponse>> getApplications(@PathVariable("recruitment-post-id") long recruitmentPostId) {
        return ApiResponse.success(List.of(new ApplicationResponse()));
    }
}
