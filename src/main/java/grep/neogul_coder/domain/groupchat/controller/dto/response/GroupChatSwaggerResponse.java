package grep.neogul_coder.domain.groupchat.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Swagger용 채팅 메시지 응답 DTO")
public class GroupChatSwaggerResponse {

    @Schema(description = "보낸 사람 ID", example = "1")
    private Long senderId;

    @Schema(description = "보낸 사람 닉네임", example = "강현")
    private String senderNickname;

    @Schema(description = "프로필 이미지 URL", example = "https://ganghyeon.jpg")
    private String profileImageUrl;

    @Schema(description = "채팅방 ID", example = "101")
    private Long roomId;

    @Schema(description = "보낸 메시지", example = "안녕하세요!")
    private String message;

    @Schema(description = "보낸 시간", example = "2025-07-07T17:45:00")
    private LocalDateTime sentAt;

    public GroupChatSwaggerResponse(Long senderId, String senderNickname, String profileImageUrl, Long roomId, String message, LocalDateTime sentAt) {
        this.senderId = senderId;
        this.senderNickname = senderNickname;
        this.profileImageUrl = profileImageUrl;
        this.roomId = roomId;
        this.message = message;
        this.sentAt = sentAt;
    }

    public static GroupChatSwaggerResponse of(Long senderId, String nickname, String profileUrl, Long roomId, String message, LocalDateTime time) {
        return new GroupChatSwaggerResponse(senderId, nickname, profileUrl, roomId, message, time);
    }


    public Long getSenderId() { return senderId; }
    public String getSenderNickname() { return senderNickname; }
    public String getProfileImageUrl() { return profileImageUrl; }
    public Long getRoomId() { return roomId; }
    public String getMessage() { return message; }
    public LocalDateTime getSentAt() { return sentAt; }
}
