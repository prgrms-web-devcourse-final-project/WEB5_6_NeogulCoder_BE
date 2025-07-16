package grep.neogul_coder.domain.recruitment.post.controller;

import grep.neogul_coder.domain.recruitment.post.controller.dto.request.RecruitmentPostCreateRequest;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.ParticipatedStudyInfo;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.ParticipatedStudyNamesInfo;
import grep.neogul_coder.domain.recruitment.post.service.RecruitmentPostSaveService;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/recruitment-posts")
@RequiredArgsConstructor
@RestController
public class RecruitmentPostSaveController implements RecruitmentPostSaveSpecification {

    private final RecruitmentPostSaveService recruitmentPostService;

    @PostMapping
    public ApiResponse<Void> save(@Valid @RequestBody RecruitmentPostCreateRequest request,
                                  @AuthenticationPrincipal Principal userDetails) {
        recruitmentPostService.create(request.toServiceRequest(), userDetails.getUserId());
        return ApiResponse.noContent();
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
}
