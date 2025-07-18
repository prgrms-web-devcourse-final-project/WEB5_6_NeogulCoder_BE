package grep.neogul_coder.domain.admin.controller.dto.response;

import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.enums.Category;
import lombok.Builder;
import lombok.Data;

@Data
public class AdminStudyResponse {

    private String name;
    private Category category;
    private boolean isFinished;
    private boolean activated;

    public static AdminStudyResponse from(Study study) {
        return  AdminStudyResponse.builder()
            .name(study.getName())
            .category(study.getCategory())
            .isFinished(study.isFinished())
            .activated(study.getActivated())
            .build();
    }

    @Builder
    private AdminStudyResponse(String name, Category category, boolean isFinished, boolean activated) {
        this.name = name;
        this.category = category;
        this.isFinished = isFinished;
        this.activated = activated;
    }

}
