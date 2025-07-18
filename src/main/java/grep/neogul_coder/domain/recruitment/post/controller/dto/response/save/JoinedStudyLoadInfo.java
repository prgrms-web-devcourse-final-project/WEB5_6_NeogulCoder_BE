package grep.neogul_coder.domain.recruitment.post.controller.dto.response.save;

import grep.neogul_coder.domain.study.Study;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class JoinedStudyLoadInfo {

    @Schema(example = "IT", description = "카테고리")
    private String category;

    @Schema(example = "경주 경리단길", description = "지역")
    private String location;

    @Schema(example = "ONLINE", description = "진행 방식")
    private String studyType;

    @Schema(example = "2025-07-13", description = "스터디 시작 날짜")
    private LocalDateTime startDate;

    @Schema(example = "2025-07-14", description = "스터디 종료 날짜")
    private LocalDateTime endDate;

    @Schema(example = "3", description = "남은 모집 인원")
    private long remainSlots;

    @Builder
    private JoinedStudyLoadInfo(Study study, long remainSlots) {
        this.category = study.getCategory().name();
        this.location = study.getLocation();
        this.studyType = study.getStudyType().name();
        this.startDate = study.getStartDate();
        this.endDate = study.getEndDate();
        this.remainSlots = remainSlots;
    }

    public static JoinedStudyLoadInfo of(Study study, long remainSlots) {
        return JoinedStudyLoadInfo.builder()
                .study(study)
                .remainSlots(remainSlots)
                .build();
    }
}
