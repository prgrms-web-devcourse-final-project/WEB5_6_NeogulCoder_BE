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
public class StudyInfoResponse {

    @Schema(description = "대표 이미지", example = "http://localhost:8083/image.jpg")
    private final String imageUrl;

    @Schema(description = "스터디 이름", example = "자바 스터디")
    private final String name;

    @Schema(description = "카테고리", example = "IT")
    private final Category category;

    @Schema(description = "인원수", example = "6")
    private final int capacity;

    @Schema(description = "타입", example = "ONLINE")
    private final StudyType studyType;

    @Schema(description = "지역", example = "서울")
    private final String location;

    @Schema(description = "시작일", example = "2025-07-15")
    private final LocalDateTime startDate;

    @NotNull
    @Schema(description = "종료일", example = "2025-07-28")
    private final LocalDateTime endDate;

    @Schema(description = "스터디 소개", example = "자바 스터디입니다.")
    private final String introduction;

    @Schema(description = "스터디 멤버 목록")
    private final List<StudyMemberResponse> members;

    @Builder
    private StudyInfoResponse(String imageUrl, String name, Category category, int capacity, StudyType studyType, String location,
                              LocalDateTime startDate, LocalDateTime endDate, String introduction, List<StudyMemberResponse> members) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.category = category;
        this.capacity = capacity;
        this.studyType = studyType;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.introduction = introduction;
        this.members = members;
    }

    public static StudyInfoResponse from(Study study, List<StudyMemberResponse> members) {
        return StudyInfoResponse.builder()
            .imageUrl(study.getImageUrl())
            .name(study.getName())
            .category(study.getCategory())
            .capacity(study.getCapacity())
            .studyType(study.getStudyType())
            .location(study.getLocation())
            .startDate(study.getStartDate())
            .endDate(study.getEndDate())
            .introduction(study.getIntroduction())
            .members(members)
            .build();
    }
}
