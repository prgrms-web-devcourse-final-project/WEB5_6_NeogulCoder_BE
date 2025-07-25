package grep.neogulcoder.domain.groupchat.controller.dto.requset;

import grep.neogulcoder.domain.groupchat.entity.GroupChatMessage;
import grep.neogulcoder.domain.groupchat.entity.GroupChatRoom;
import io.swagger.v3.oas.annotations.Hidden;
import java.time.LocalDateTime;
import lombok.Getter;

@Hidden
@Getter
public class GroupChatMessageRequestDto {
    private Long studyId;
    private Long senderId;
    private String message;

    public GroupChatMessageRequestDto() {}

    public GroupChatMessageRequestDto(Long studyId, Long senderId, String message) {
        this.studyId = studyId;
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
