package grep.neogulcoder.domain.admin.controller.dto.response;

import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.enums.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "관리자용 스터디 응답 DTO")
public class AdminStudyResponse {

    @Schema(description = "스터디 ID", example = "101")
    private Long id;

    @Schema(description = "스터디 이름", example = "Spring Boot 스터디")
    private String name;

    @Schema(description = "스터디 카테고리", example = "BACKEND")
    private Category category;

    @Schema(description = "스터디 종료 여부", example = "false")
    private boolean finished;

    @Schema(description = "활성화 여부", example = "true")
    private boolean activated;

    @Builder
    private AdminStudyResponse(Long id, String name, Category category, boolean isFinished,
        boolean activated) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.finished = isFinished;
        this.activated = activated;
    }

    public static AdminStudyResponse from(Study study) {
        return AdminStudyResponse.builder()
            .id(study.getId())
            .name(study.getName())
            .category(study.getCategory())
            .isFinished(study.isFinished())
            .activated(study.isActivated())
            .build();
    }

}
