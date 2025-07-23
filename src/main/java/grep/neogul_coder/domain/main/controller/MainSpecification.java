package grep.neogul_coder.domain.main.controller;

import grep.neogul_coder.domain.main.controller.dto.response.MainResponse;
import grep.neogul_coder.domain.study.enums.Category;
import grep.neogul_coder.domain.study.enums.StudyType;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;

@Tag(name = "Main", description = "메인화면 API")
public interface MainSpecification {

    @Operation(summary = "메인 화면 조회", description = "메인 화면을 조회합니다.")
    ApiResponse<MainResponse> getMain(Pageable pageable, Category category, StudyType studyType, String keyword, Principal userDetails);
}
