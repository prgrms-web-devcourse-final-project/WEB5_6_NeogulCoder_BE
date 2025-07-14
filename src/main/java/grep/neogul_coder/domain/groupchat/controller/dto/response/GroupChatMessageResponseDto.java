package grep.neogul_coder.domain.groupchat.controller.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class GroupChatMessageResponseDto {

    private Long id;                    // 메시지 고유 ID
    private Long roomId;                // 채팅방 ID
    private Long senderId;              // 보낸 사람 ID
    private String senderNickname;      // 보낸 사람 닉네임
    private String profileImageUrl;     // 프로필 이미지 URL
    private String message;             // 메시지 내용
    private LocalDateTime sentAt;       // 보낸 시간

    public GroupChatMessageResponseDto() {
    }

    public GroupChatMessageResponseDto(Long id, Long roomId, Long senderId,
        String senderNickname, String profileImageUrl,
        String message, LocalDateTime sentAt) {
        this.id = id;
        this.roomId = roomId;
        this.senderId = senderId;
        this.senderNickname = senderNickname;
        this.profileImageUrl = profileImageUrl;
        this.message = message;
        this.sentAt = sentAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}
