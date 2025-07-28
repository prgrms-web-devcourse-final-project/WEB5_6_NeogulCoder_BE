package grep.neogulcoder.domain.study.controller.dto.response;

import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.enums.Category;
import grep.neogulcoder.domain.study.enums.StudyType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

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

    @Schema(description = "카테고리", example = "IT")
    private Category category;

    @Schema(description = "인원수", example = "6")
    private int capacity;

    @Schema(description = "시작일", example = "2025-07-15")
    private LocalDateTime startDate;

    @NotNull
    @Schema(description = "종료일", example = "2025-07-28")
    private LocalDateTime endDate;

    @Schema(description = "스터디 멤버 목록")
    private List<StudyMemberResponse> members;

    @Builder
    private StudyHeaderResponse(String name, String introduction, String imageUrl, StudyType studyType, String location, Category category,
                                int capacity, LocalDateTime startDate, LocalDateTime endDate, List<StudyMemberResponse> members) {
        this.name = name;
        this.introduction = introduction;
        this.imageUrl = imageUrl;
        this.studyType = studyType;
        this.location = location;
        this.category = category;
        this.capacity = capacity;
        this.startDate = startDate;
        this.endDate = endDate;
        this.members = members;
    }

    public static StudyHeaderResponse from(Study study, List<StudyMemberResponse> members) {
        return StudyHeaderResponse.builder()
            .name(study.getName())
            .introduction(study.getIntroduction())
            .imageUrl(study.getImageUrl())
            .studyType(study.getStudyType())
            .location(study.getLocation())
            .category(study.getCategory())
            .capacity(study.getCapacity())
            .startDate(study.getStartDate())
            .endDate(study.getEndDate())
            .members(members)
            .build();
    }
}
