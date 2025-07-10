package grep.neogul_coder.domain.study.controller.dto.response;

import grep.neogul_coder.domain.study.enums.Category;
import grep.neogul_coder.domain.study.enums.StudyType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class StudyResponse {

    @Schema(description = "스터디 번호", example = "2")
    private int studyId;

    @Schema(description = "기존 스터디 번호", example = "1")
    private int originStudyId;

    @Schema(description = "스터디 이름", example = "자바 스터디")
    private String name;

    @Schema(description = "카테고리", example = "IT")
    private Category category;

    @Schema(description = "정원", example = "4")
    private int capacity;

    @Schema(description = "참여 인원수", example = "3")
    private int currentCount;

    @Schema(description = "타입", example = "ONLINE")
    private StudyType studyType;

    @Schema(description = "지역", example = "서울")
    private String location;

    @Schema(description = "시작일", example = "2025-07-15")
    private LocalDate startDate;

    @Schema(description = "종료일", example = "2025-07-28")
    private LocalDate endDate;

    @Schema(description = "스터디 소개", example = "자바 스터디")
    private String introduction;

    @Schema(description = "대표 이미지", example = "http://localhost:8083/image.jpg")
    private String imageUrl;

    @Schema(description = "종료 여부", example = "false")
    private boolean isActivated;

    @Schema(description = "삭제 여부", example = "false")
    private boolean isDeleted;
}
