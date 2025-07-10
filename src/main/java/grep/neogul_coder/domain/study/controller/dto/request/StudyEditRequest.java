package grep.neogul_coder.domain.study.controller.dto.request;

import grep.neogul_coder.domain.study.enums.Category;
import grep.neogul_coder.domain.study.enums.StudyType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class StudyEditRequest {

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
    private LocalDate startDate;

    @Schema(description = "스터디 소개", example = "자바 스터디")
    private String introduction;

    @NotBlank
    @Schema(description = "대표 이미지", example = "http://localhost:8083/image.jpg")
    private String imageUrl;
}
