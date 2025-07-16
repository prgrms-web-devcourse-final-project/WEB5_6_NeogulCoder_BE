package grep.neogul_coder.domain.recruitment.post.controller;

import grep.neogul_coder.domain.recruitment.post.controller.dto.request.RecruitmentPostCreateRequest;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.LoadParticipatedStudyInfo;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.ParticipatedStudiesInfo;
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
    public ApiResponse<ParticipatedStudiesInfo> getParticipatedStudyInfo(@AuthenticationPrincipal Principal userDetails) {
        ParticipatedStudiesInfo response = recruitmentPostService.getParticipatedStudyInfo(userDetails.getUserId());
        return ApiResponse.success(response);
    }

    @GetMapping("/studies/{study-id}")
    public ApiResponse<LoadParticipatedStudyInfo> loadParticipatedStudyInfo(@PathVariable("study-id") long studyId,
                                                                            @AuthenticationPrincipal Principal userDetails) {
        LoadParticipatedStudyInfo response = recruitmentPostService.loadParticipatedStudyInfo(studyId, userDetails.getUserId());
        return ApiResponse.success(response);
    }
}
