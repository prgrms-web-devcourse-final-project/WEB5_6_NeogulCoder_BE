package grep.neogul_coder.domain.study.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class StudyExtensionResponse {

    @Schema(description = "스터디 Id", example = "1")
    private boolean studyId;

    @Schema(description = "연장 여부", example = "true")
    private boolean isExtended;

    @Schema(description = "연장 스터디 참여 여부 목록")
    private List<ExtendParticipationResponse> members;
}
