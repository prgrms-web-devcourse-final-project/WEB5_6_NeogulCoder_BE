package grep.neogulcoder.domain.groupchat.controller.dto.response;

import grep.neogulcoder.domain.groupchat.entity.GroupChatMessage;
import grep.neogulcoder.domain.users.entity.User;
import io.swagger.v3.oas.annotations.Hidden;
import java.time.LocalDateTime;
import lombok.Getter;

@Hidden
@Getter
public class GroupChatMessageResponseDto {

    private Long id;                    // 메시지 고유 ID
    private Long studyId;                // 채팅방 ID
    private Long senderId;              // 보낸 사람 ID
    private String senderNickname;      // 보낸 사람 닉네임
    private String profileImageUrl;     // 프로필 이미지 URL
    private String message;             // 메시지 내용
    private LocalDateTime sentAt;       // 보낸 시간

    private GroupChatMessageResponseDto(Long id, Long studyId, Long senderId,
        String senderNickname, String profileImageUrl,
        String message, LocalDateTime sentAt) {
        this.id = id;
        this.studyId = studyId;
        this.senderId = senderId;
        this.senderNickname = senderNickname;
        this.profileImageUrl = profileImageUrl;
        this.message = message;
        this.sentAt = sentAt;
    }

    public static GroupChatMessageResponseDto from(GroupChatMessage message, User sender) {
        return new GroupChatMessageResponseDto(
            message.getMessageId(),
            message.getGroupChatRoom().getStudyId(),
            sender.getId(),
            sender.getNickname(),
            sender.getProfileImageUrl(),
            message.getMessage(),
            message.getSentAt()
        );
    }
}
