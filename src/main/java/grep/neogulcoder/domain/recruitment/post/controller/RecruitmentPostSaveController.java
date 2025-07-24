package grep.neogulcoder.domain.recruitment.post.controller;

import grep.neogulcoder.domain.recruitment.post.controller.dto.request.save.RecruitmentPostCreateRequest;
import grep.neogulcoder.domain.recruitment.post.controller.dto.response.save.JoinedStudyLoadInfo;
import grep.neogulcoder.domain.recruitment.post.controller.dto.response.save.JoinedStudiesInfo;
import grep.neogulcoder.domain.recruitment.post.service.RecruitmentPostSaveService;
import grep.neogulcoder.global.auth.Principal;
import grep.neogulcoder.global.response.ApiResponse;
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
    public ApiResponse<Long> save(@Valid @RequestBody RecruitmentPostCreateRequest request,
                                  @AuthenticationPrincipal Principal userDetails) {
        long postId = recruitmentPostService.create(request.toServiceRequest(), userDetails.getUserId());
        return ApiResponse.success(postId);
    }

    @GetMapping("/studies")
    public ApiResponse<JoinedStudiesInfo> getJoinedStudyInfo(@AuthenticationPrincipal Principal userDetails) {
        JoinedStudiesInfo response = recruitmentPostService.getJoinedStudyInfo(userDetails.getUserId());
        return ApiResponse.success(response);
    }

    @GetMapping("/studies/{study-id}")
    public ApiResponse<JoinedStudyLoadInfo> getJoinedStudyLoadInfo(@PathVariable("study-id") long studyId,
                                                                   @AuthenticationPrincipal Principal userDetails) {
        JoinedStudyLoadInfo response = recruitmentPostService.getJoinedStudyLoadInfo(studyId, userDetails.getUserId());
        return ApiResponse.success(response);
    }
}
