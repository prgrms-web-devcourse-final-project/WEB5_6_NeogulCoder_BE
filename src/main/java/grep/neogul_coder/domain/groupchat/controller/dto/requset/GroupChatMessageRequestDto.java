package grep.neogul_coder.domain.groupchat.controller.dto.requset;

import lombok.Getter;

@Getter
public class GroupChatMessageRequestDto {
    private Long roomId;
    private Long senderId;
    private String message;

    public GroupChatMessageRequestDto() {}

    public GroupChatMessageRequestDto(Long roomId, Long senderId, String message) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.message = message;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
