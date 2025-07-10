package grep.neogul_coder.domain.studyapplication.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class StudyApplicationCreateRequest {

    @NotBlank
    @Schema(description = "스터디 신청 지원 동기", example = "자바를 더 공부하고싶어 지원하였습니다.")
    private String applicationReason;
}
