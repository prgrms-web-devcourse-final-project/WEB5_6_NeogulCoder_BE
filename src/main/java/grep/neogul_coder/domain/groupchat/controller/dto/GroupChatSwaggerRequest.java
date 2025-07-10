package grep.neogul_coder.domain.groupchat.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Swagger용 채팅 메시지 전송 요청 DTO")
public class GroupChatSwaggerRequest {

    @Schema(description = "보낸 사람 ID", example = "456")
    private Long senderId;

    @Schema(description = "채팅방 ID", example = "100")
    private Long roomId;

    @Schema(description = "보낼 메시지", example = "안녕하세요!")
    private String message;


    public void setSenderId(Long senderId) { this.senderId = senderId; }

    public void setRoomId(Long roomId) { this.roomId = roomId; }

    public void setMessage(String message) { this.message = message; }
}
