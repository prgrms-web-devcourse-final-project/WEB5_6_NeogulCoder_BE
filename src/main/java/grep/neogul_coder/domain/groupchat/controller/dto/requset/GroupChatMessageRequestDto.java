package grep.neogul_coder.domain.groupchat.controller.dto.requset;

import grep.neogul_coder.domain.groupchat.entity.GroupChatMessage;
import grep.neogul_coder.domain.groupchat.entity.GroupChatRoom;
import io.swagger.v3.oas.annotations.Hidden;
import java.time.LocalDateTime;
import lombok.Getter;

@Hidden
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

    public GroupChatMessage toEntity(GroupChatRoom room, Long senderId) {
        return new GroupChatMessage(
            room,
            senderId,
            this.message,
            LocalDateTime.now()
        );
    }
}
