package grep.neogul_coder.domain.admin.controller.dto.response;

import grep.neogul_coder.domain.study.Study;
import lombok.Builder;
import lombok.Data;

@Data
public class AdminStudyResponse {

    private String name;
    private String category;
    private boolean isFinished;
    private boolean activated;

    @Builder
    private AdminStudyResponse(String name, String category, boolean isFinished, boolean activated) {
        this.name = name;
        this.category = category;
        this.isFinished = isFinished;
        this.activated = activated;
    }

    public static AdminStudyResponse from(Study study) {
        return new AdminStudyResponse(
            study.getName(),
            study.getCategory().name(),
            study.isFinished(),
            study.getActivated()
        );
    }

}
