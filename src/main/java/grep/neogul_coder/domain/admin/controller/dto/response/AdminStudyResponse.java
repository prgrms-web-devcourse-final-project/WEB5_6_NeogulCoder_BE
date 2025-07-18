package grep.neogul_coder.domain.admin.controller.dto.response;

import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.enums.Category;
import lombok.Builder;
import lombok.Data;

@Data
public class AdminStudyResponse {

    private Long id;
    private String name;
    private Category category;
    private boolean isFinished;
    private boolean activated;

    @Builder
    private AdminStudyResponse(Long id, String name, Category category, boolean isFinished, boolean activated) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.isFinished = isFinished;
        this.activated = activated;
    }

    public static AdminStudyResponse from(Study study) {
        return  AdminStudyResponse.builder()
            .id(study.getId())
            .name(study.getName())
            .category(study.getCategory())
            .isFinished(study.isFinished())
            .activated(study.getActivated())
            .build();
    }

}
