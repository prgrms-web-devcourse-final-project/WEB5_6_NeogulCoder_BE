package grep.neogul_coder.domain.study.controller.dto.request;

import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.enums.Category;
import grep.neogul_coder.domain.study.enums.StudyType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StudyCreateRequest {

    @NotBlank
    @Schema(description = "스터디 이름", example = "자바 스터디")
    private String name;

    @NotNull
    @Schema(description = "카테고리", example = "IT")
    private Category category;

    @Min(1)
    @Schema(description = "정원", example = "4")
    private int capacity;

    @NotNull
    @Schema(description = "타입", example = "ONLINE")
    private StudyType studyType;

    @Schema(description = "지역", example = "서울")
    private String location;

    @NotNull
    @Schema(description = "시작일", example = "2025-07-15")
    private LocalDateTime startDate;

    @NotNull
    @Schema(description = "종료일", example = "2025-07-28")
    private LocalDateTime endDate;

    @Schema(description = "스터디 소개", example = "자바 스터디입니다.")
    private String introduction;

    @NotBlank
    @Schema(description = "대표 이미지", example = "http://localhost:8083/image.jpg")
    private String imageUrl;

    private StudyCreateRequest() {}

    @Builder
    private StudyCreateRequest(String name, Category category, int capacity, StudyType studyType, String location,
                               LocalDateTime startDate, LocalDateTime endDate, String introduction, String imageUrl) {
        this.name = name;
        this.category = category;
        this.capacity = capacity;
        this.studyType = studyType;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.introduction = introduction;
        this.imageUrl = imageUrl;
    }

    public Study toEntity() {
        return Study.builder()
            .name(this.name)
            .category(this.category)
            .capacity(this.capacity)
            .studyType(this.studyType)
            .location(this.location)
            .startDate(this.startDate)
            .endDate(this.endDate)
            .introduction(this.introduction)
            .imageUrl(this.imageUrl)
            .build();
    }
}
