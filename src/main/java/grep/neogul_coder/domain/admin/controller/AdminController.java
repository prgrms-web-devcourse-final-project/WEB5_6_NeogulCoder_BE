package grep.neogul_coder.domain.admin.controller;

import grep.neogul_coder.domain.recruitment.post.service.RecruitmentPostService;
import grep.neogul_coder.domain.study.service.StudyService;
import grep.neogul_coder.domain.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final StudyService studyService;
    private final RecruitmentPostService recruitmentPostService;


}
