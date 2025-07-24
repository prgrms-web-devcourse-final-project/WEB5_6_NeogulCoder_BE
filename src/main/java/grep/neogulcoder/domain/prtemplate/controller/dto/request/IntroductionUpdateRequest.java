package grep.neogulcoder.domain.prtemplate.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "회원 자기소개 수정 요청 DTO")
public class IntroductionUpdateRequest {

    @Size(max = 500, message = "자기소개는 최대 500자까지 입력할 수 있습니다.")
    @Schema(description = "자기소개 내용", example = "안녕하세요! 반갑습니다.")
    private String introduction;
}