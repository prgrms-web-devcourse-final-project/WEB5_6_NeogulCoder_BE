package grep.neogul_coder.domain.study.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExtendStudyRequest {

    @NotNull
    @Schema(description = "연장 스터디의 종료일", example = "2025-07-15")
    private LocalDateTime newEndDate;
}
