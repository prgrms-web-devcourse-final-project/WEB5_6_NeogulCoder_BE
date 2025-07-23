package grep.neogulcoder.domain.study.controller.dto.request;

import grep.neogulcoder.domain.study.Study;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExtendStudyRequest {

    @NotNull
    @Schema(description = "연장 스터디의 종료일", example = "2025-07-15")
    private LocalDateTime newEndDate;

    private ExtendStudyRequest() {}

    @Builder
    private ExtendStudyRequest(LocalDateTime newEndDate) {
        this.newEndDate = newEndDate;
    }

    public Study toEntity(Study study) {
        return Study.builder()
            .originStudyId(study.getId())
            .name(study.getName())
            .category(study.getCategory())
            .capacity(study.getCapacity())
            .studyType(study.getStudyType())
            .location(study.getLocation())
            .startDate(study.getEndDate().plusDays(1))
            .endDate(this.newEndDate)
            .introduction(study.getIntroduction())
            .imageUrl(study.getImageUrl())
            .build();
    }
}
