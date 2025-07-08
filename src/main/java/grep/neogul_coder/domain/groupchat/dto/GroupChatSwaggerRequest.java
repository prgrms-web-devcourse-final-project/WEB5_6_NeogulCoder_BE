package grep.neogul_coder.domain.groupchat.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Swagger용 채팅 메시지 전송 요청 DTO")
public class GroupChatSwaggerRequest {

    @Schema(description = "보낸 사람 ID", example = "3")
    private Long senderId;

    @Schema(description = "채팅방 ID", example = "101")
    private Long roomId;

    @Schema(description = "보낼 메시지", example = "안녕하세요!")
    private String message;



    public Long getSenderId() { return senderId; }
    public void setSenderId(Long senderId) { this.senderId = senderId; }

    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
