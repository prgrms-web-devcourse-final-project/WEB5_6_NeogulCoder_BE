package grep.neogul_coder.domain.prtemplate.controller.dto.request;

import grep.neogul_coder.domain.prtemplate.entity.Link;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

@Data
@Schema(description = "회원 PR 정보 수정 요청 DTO")
public class PrUpdateRequest {

    @Schema(description = "현재 위치", example = "서울특별시")
    private String location;

    @Size(max = 2, message = "링크는 최대 2개 까지 가능합니다.")
    @Schema(description = "PR 링크 목록", example = "[\"https://instagram.com/example\"]")
    private List<LinkUpdateRequest> prUrls;
}