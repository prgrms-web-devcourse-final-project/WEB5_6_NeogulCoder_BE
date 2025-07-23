package grep.neogulcoder.domain.study.controller.dto.response;

import grep.neogulcoder.domain.study.Study;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StudyImageResponse {

    @Schema(description = "스터디 번호", example = "3")
    private Long studyId;

    @Schema(description = "스터디 이름", example = "자바 스터디")
    private String name;

    @Schema(description = "대표 이미지", example = "http://localhost:8083/image.jpg")
    private String imageUrl;

    @Builder
    private StudyImageResponse(Long studyId, String name, String imageUrl) {
        this.studyId = studyId;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public static StudyImageResponse from(Study study) {
        return StudyImageResponse.builder()
            .studyId(study.getId())
            .name(study.getName())
            .imageUrl(study.getImageUrl())
            .build();
    }
}
