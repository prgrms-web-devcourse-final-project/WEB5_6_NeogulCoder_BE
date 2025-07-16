package grep.neogul_coder.domain.recruitment.post.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ParticipatedStudyLoadInfo {

    @Schema(example = "IT", description = "카테고리")
    private String category;

    @Schema(example = "경주 경리단길", description = "지역")
    private String location;

    @Schema(example = "ONLINE", description = "진행 방식")
    private String studyType;

    @Schema(example = "2025-07-13", description = "스터디 시작 날짜")
    private LocalDate startDate;

    @Schema(example = "2025-07-14", description = "스터디 종료 날짜")
    private LocalDate endDate;

    @Schema(example = "3", description = "모집 인원")
    private int recruitmentCount;
}
