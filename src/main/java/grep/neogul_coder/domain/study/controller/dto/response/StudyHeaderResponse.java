package grep.neogul_coder.domain.study.controller.dto.response;

import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.enums.StudyType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StudyHeaderResponse {

    @Schema(description = "스터디 이름", example = "자바 스터디")
    private String name;

    @Schema(description = "스터디 소개", example = "자바 스터디")
    private String introduction;

    @Schema(description = "대표 이미지", example = "http://localhost:8083/image.jpg")
    private String imageUrl;

    @Schema(description = "타입", example = "ONLINE")
    private StudyType studyType;

    @Schema(description = "지역", example = "서울")
    private String location;

    @Builder
    private StudyHeaderResponse(String name, String introduction, String imageUrl,
                                StudyType studyType, String location) {
        this.name = name;
        this.introduction = introduction;
        this.imageUrl = imageUrl;
        this.studyType = studyType;
        this.location = location;
    }

    public static StudyHeaderResponse from(Study study) {
        return StudyHeaderResponse.builder()
            .name(study.getName())
            .introduction(study.getIntroduction())
            .imageUrl(study.getImageUrl())
            .studyType(study.getStudyType())
            .location(study.getLocation())
            .build();
    }
}
