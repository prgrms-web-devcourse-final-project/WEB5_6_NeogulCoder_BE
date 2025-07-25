package grep.neogulcoder.domain.groupchat.controller.dto.requset;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Swagger용 채팅 메시지 전송 요청 DTO")
public class GroupChatSwaggerRequest {

    @Schema(description = "보낸 사람 ID", example = "456")
    private Long senderId;

    @Schema(description = "스터디 ID", example = "100")
    private Long studyId;

    @Schema(description = "보낼 메시지", example = "안녕하세요!")
    private String message;
}
