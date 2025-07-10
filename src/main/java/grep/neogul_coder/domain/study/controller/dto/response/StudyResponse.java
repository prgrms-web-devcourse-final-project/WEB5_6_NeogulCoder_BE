package grep.neogul_coder.domain.study.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class StudyResponse {

    @Schema(description = "스터디 총 기간", example = "320")
    private int totalDays;

    @Schema(description = "정원", example = "4")
    private int capacity;

    @Schema(description = "인원수", example = "3")
    private int currentCount;

    // 출석, 달력, 공지 게시글, 최신 게시글 추가 예정
}
