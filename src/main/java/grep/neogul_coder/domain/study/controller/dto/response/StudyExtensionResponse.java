package grep.neogul_coder.domain.study.controller.dto.response;

import grep.neogul_coder.domain.study.Study;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class StudyExtensionResponse {

    @Schema(description = "스터디 Id", example = "1")
    private Long studyId;

    @Schema(description = "연장 여부", example = "true")
    private boolean isExtended;

    @Schema(description = "연장 스터디 참여 여부 목록")
    private List<ExtendParticipationResponse> members;

    @Builder
    private StudyExtensionResponse(Long studyId, boolean isExtended, List<ExtendParticipationResponse> members) {
        this.studyId = studyId;
        this.isExtended = isExtended;
        this.members = members;
    }

    public static StudyExtensionResponse from(Study study, List<ExtendParticipationResponse> members) {
        return StudyExtensionResponse.builder()
            .studyId(study.getId())
            .isExtended(study.getOriginStudyId() != null)
            .members(members)
            .build();
    }
}
