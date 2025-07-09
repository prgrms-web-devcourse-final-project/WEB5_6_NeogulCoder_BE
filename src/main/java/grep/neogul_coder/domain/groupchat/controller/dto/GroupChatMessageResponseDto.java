//package grep.neogul_coder.domain.groupchat.controller.dto;
//
//import java.time.LocalDateTime;
//
//public class GroupChatMessageResponseDto {
//
//    private Long id;              // 메시지 고유 ID
//    private Long roomId;          // (선택) 프론트 팀에서 필요시
//    private Long senderId;        // user 추적
//    private String content;       // 메시지 내용
//    private String senderName;    // 메시지 보낸 사람(닉네임)
//    private String senderProfileUrl; // 메시지 보낸 사람의 프로필 사진 URL
//    private LocalDateTime sentAt; // 메시지 보낸 시각
//
//    public GroupChatMessageResponseDto() {
//    }
//
//    public GroupChatMessageResponseDto(Long id, Long roomId, Long senderId, String content,
//        String senderName,String senderProfileUrl, LocalDateTime sentAt) {
//        this.id = id;
//        this.roomId = roomId;
//        this.senderId = senderId;
//        this.content = content;
//        this.senderName = senderName;
//        this.senderProfileUrl = senderProfileUrl;
//        this.sentAt = sentAt;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getRoomId() {
//        return roomId;
//    }
//
//    public void setRoomId(Long roomId) {
//        this.roomId = roomId;
//    }
//
//    public Long getSenderId() {
//        return senderId;
//    }
//
//    public void setSenderId(Long senderId) {
//        this.senderId = senderId;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public String getSenderName() {
//        return senderName;
//    }
//
//    public void setSenderName(String senderName) {
//        this.senderName = senderName;
//    }
//
//    public String getSenderProfileUrl() {
//        return senderProfileUrl;
//    }
//
//    public void setSenderProfileUrl(String senderProfileUrl) {
//        this.senderProfileUrl = senderProfileUrl;
//    }
//
//    public LocalDateTime getSentAt() {
//        return sentAt;
//    }
//
//    public void setSentAt(LocalDateTime sentAt) {
//        this.sentAt = sentAt;
//    }
//}
