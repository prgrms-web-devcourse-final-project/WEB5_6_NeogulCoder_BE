package grep.neogul_coder.domain.study.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ExtendStudyRequest {

    @Schema(description = "연장 스터디의 종료일", example = "2025-07-15")
    private LocalDate newEndDate;
}
