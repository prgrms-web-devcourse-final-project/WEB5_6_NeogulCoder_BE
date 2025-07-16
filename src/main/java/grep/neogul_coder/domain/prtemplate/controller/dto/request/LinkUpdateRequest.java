package grep.neogul_coder.domain.prtemplate.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LinkUpdateRequest {

    @Schema(description = "링크 이름", example = "인스타그램")
    private String urlName;

    @Schema(description = "링크 URL", example = "https://instagram.com/example")
    private String prUrl;

}
