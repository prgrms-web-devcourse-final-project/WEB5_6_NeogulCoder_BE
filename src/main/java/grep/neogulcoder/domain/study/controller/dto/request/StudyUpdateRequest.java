package grep.neogulcoder.domain.study.controller.dto.request;

import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.enums.Category;
import grep.neogulcoder.domain.study.enums.StudyType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StudyUpdateRequest {

    @NotBlank
    @Schema(description = "스터디 이름", example = "자바 스터디")
    private String name;

    @NotNull
    @Schema(description = "카테고리", example = "IT")
    private Category category;

    @Schema(description = "정원", example = "4")
    private int capacity;

    @NotNull
    @Schema(description = "타입", example = "ONLINE")
    private StudyType studyType;

    @Schema(description = "지역", example = "서울")
    private String location;

    @Schema(description = "시작일", example = "2025-07-15")
    private LocalDateTime startDate;

    @Schema(description = "스터디 소개", example = "자바 스터디입니다.")
    private String introduction;

    private StudyUpdateRequest() {}

    @Builder
    private StudyUpdateRequest(String name, Category category, int capacity, StudyType studyType,
                               String location, LocalDateTime startDate, String introduction) {
        this.name = name;
        this.category = category;
        this.capacity = capacity;
        this.studyType = studyType;
        this.location = location;
        this.startDate = startDate;
        this.introduction = introduction;
    }

    public Study toEntity(String imageUrl) {
        return Study.builder()
            .name(this.name)
            .category(this.category)
            .capacity(this.capacity)
            .studyType(this.studyType)
            .location(this.location)
            .startDate(this.startDate)
            .introduction(this.introduction)
            .imageUrl(imageUrl)
            .build();
    }
}
