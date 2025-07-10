package grep.neogul_coder.domain.study.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class DelegateLeaderRequest {

    @Schema(description = "스터디장을 위임할 회원 번호", example = "5")
    private Long newLeaderId;
}
