package grep.neogul_coder.domain.groupchat.service;

import grep.neogul_coder.domain.groupchat.entity.GroupChatMessage;
import grep.neogul_coder.domain.groupchat.entity.GroupChatRoom;
import grep.neogul_coder.domain.groupchat.controller.dto.requset.GroupChatMessageRequestDto;
import grep.neogul_coder.domain.groupchat.controller.dto.response.GroupChatMessageResponseDto;
import grep.neogul_coder.domain.groupchat.repository.GroupChatMessageRepository;
import grep.neogul_coder.domain.groupchat.repository.GroupChatRoomRepository;
import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.domain.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class GroupChatService {

    private final GroupChatMessageRepository messageRepository;
    private final GroupChatRoomRepository roomRepository;
    private final UserRepository userRepository;

    public GroupChatService(GroupChatMessageRepository messageRepository,
        GroupChatRoomRepository roomRepository,
        UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    public GroupChatMessageResponseDto saveMessage(GroupChatMessageRequestDto requestDto) {
        // 필요한 도메인 객체 조회
        GroupChatRoom room = roomRepository.findById(requestDto.getRoomId())
            .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
        User sender = userRepository.findById(requestDto.getSenderId())
            .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        // 메시지 생성 및 저장
        GroupChatMessage message = new GroupChatMessage();
        message.setGroupChatRoom(room);                      // 엔티티에 있는 필드명 맞게 사용
        message.setUserId(sender.getId());                   // 연관관계 없이 userId만 저장
        message.setMessage(requestDto.getMessage());
        message.setSentAt(LocalDateTime.now());

        messageRepository.save(message);

        // 응답 DTO 구성
        return new GroupChatMessageResponseDto(
            message.getMessageId(),
            message.getGroupChatRoom().getRoomId(),   // ← roomId 필드가 필요하면 여기서 꺼내야 함
            sender.getId(),
            sender.getNickname(),
            sender.getProfileImageUrl(),
            message.getMessage(),
            message.getSentAt()
        );
    }
}
