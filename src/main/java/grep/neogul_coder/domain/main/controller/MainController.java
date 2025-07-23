package grep.neogul_coder.domain.main.controller;

import grep.neogul_coder.domain.main.controller.dto.response.MainResponse;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.RecruitmentPostPagingInfo;
import grep.neogul_coder.domain.recruitment.post.service.RecruitmentPostService;
import grep.neogul_coder.domain.study.controller.dto.response.StudyItemResponse;
import grep.neogul_coder.domain.study.enums.Category;
import grep.neogul_coder.domain.study.enums.StudyType;
import grep.neogul_coder.domain.study.service.StudyService;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/main")
@RequiredArgsConstructor
@RestController
public class MainController implements MainSpecification {

    private final StudyService studyService;
    private final RecruitmentPostService recruitmentPostService;

    @GetMapping
    public ApiResponse<MainResponse> getMain(@PageableDefault(size = 10) Pageable pageable,
                                             @RequestParam("category") Category category,
                                             @RequestParam("studyType") StudyType studyType,
                                             @RequestParam("keyword") String keyword,
                                             @AuthenticationPrincipal Principal userDetails) {
        Long userId = userDetails.getUserId();

        List<StudyItemResponse> myStudies = studyService.getMyStudies(userId);
        RecruitmentPostPagingInfo recruitingStudies = recruitmentPostService.getPagingInfo(pageable, category, studyType, keyword, null);
        MainResponse response = MainResponse.from(myStudies, recruitingStudies);

        return ApiResponse.success(response);
    }
}
