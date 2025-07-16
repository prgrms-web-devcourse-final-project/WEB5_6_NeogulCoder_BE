package grep.neogul_coder.domain.study.controller.dto.response;

import grep.neogul_coder.domain.study.Study;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StudyImageResponse {

    @Schema(description = "스터디 번호", example = "3")
    private Long studyId;

    @Schema(description = "대표 이미지", example = "http://localhost:8083/image.jpg")
    private String imageUrl;

    @Builder
    private StudyImageResponse(Long studyId, String imageUrl) {
        this.studyId = studyId;
        this.imageUrl = imageUrl;
    }

    public static StudyImageResponse from(Study study) {
        return StudyImageResponse.builder()
            .studyId(study.getId())
            .imageUrl(study.getImageUrl())
            .build();
    }
}
