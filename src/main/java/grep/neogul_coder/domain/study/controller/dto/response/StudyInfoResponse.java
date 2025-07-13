package grep.neogul_coder.domain.study.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class StudyInfoResponse {

    @Schema(description = "대표 이미지", example = "http://localhost:8083/image.jpg")
    private String imageUrl;

    @Schema(description = "스터디 이름", example = "자바 스터디")
    private String name;

    @Schema(description = "카테고리", example = "IT")
    private String category;

    @Schema(description = "인원수", example = "6")
    private int capacity;

    @Schema(description = "타입", example = "ONLINE")
    private String studyType;

    @Schema(description = "지역", example = "서울")
    private String location;

    @Schema(description = "시작일", example = "2025-07-15")
    private LocalDate startDate;

    @NotNull
    @Schema(description = "종료일", example = "2025-07-28")
    private LocalDate endDate;

    @Schema(description = "스터디 소개", example = "자바 스터디입니다.")
    private String introduction;

    @Schema(description = "스터디 멤버 목록")
    private List<StudyMemberResponse> members;
}
