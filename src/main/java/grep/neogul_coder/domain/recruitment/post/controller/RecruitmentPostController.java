package grep.neogul_coder.domain.recruitment.post.controller;

import grep.neogul_coder.domain.recruitment.post.controller.dto.request.RecruitmentPostSaveRequest;
import grep.neogul_coder.global.response.ApiResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecruitmentPostController implements RecruitmentPostSpecification {

    @PostMapping("/studies/{study-id}")
    public ApiResponse<Void> save(@PathVariable("study-id") Long studyId,
                                  @RequestBody RecruitmentPostSaveRequest request) {
        return ApiResponse.noContent("모집글이 생성 되었습니다.");
    }
}
