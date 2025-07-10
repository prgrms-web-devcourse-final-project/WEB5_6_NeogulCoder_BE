package grep.neogul_coder.domain.study.controller.dto.response;

import grep.neogul_coder.domain.study.enums.Category;
import grep.neogul_coder.domain.study.enums.StudyType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class StudyListResponse {

    @Schema(description = "스터디 이름", example = "자바 스터디")
    private String name;

    @Schema(description = "스터디장 닉네임", example = "너굴")
    private String leaderNickname;

    @Schema(description = "정원", example = "4")
    private int capacity;

    @Schema(description = "인원수", example = "3")
    private int currentCount;

    @Schema(description = "시작일", example = "2025-07-15")
    private LocalDate startDate;

    @Schema(description = "대표 이미지", example = "http://localhost:8083/image.jpg")
    private String imageUrl;

    @Schema(description = "스터디 소개", example = "자바 스터디입니다.")
    private String introduction;

    @Schema(description = "카테고리", example = "IT")
    private Category category;

    @Schema(description = "타입", example = "ONLINE")
    private StudyType studyType;

    @Schema(description = "종료 여부", example = "false")
    private boolean isActivated;
}
