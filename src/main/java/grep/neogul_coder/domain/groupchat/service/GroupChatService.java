//package grep.neogul_coder.domain.groupchat.service;
//
//import grep.neogul_coder.domain.groupchat.domain.GroupChatMessage;
//import grep.neogul_coder.domain.groupchat.domain.GroupChatRoom;
//import grep.neogul_coder.domain.groupchat.dto.GroupChatMessageRequestDto;
//import grep.neogul_coder.domain.groupchat.dto.GroupChatMessageResponseDto;
//import grep.neogul_coder.domain.groupchat.repository.GroupChatMessageRepository;
//import grep.neogul_coder.domain.groupchat.repository.GroupChatRoomRepository;
//import grep.neogul_coder.domain.user.domain.User;
//import grep.neogul_coder.domain.user.repository.UserRepository;
//import org.springframework.stereotype.Service;
//import java.time.LocalDateTime;
//
//@Service
//public class GroupChatService {
//
//    private final GroupChatMessageRepository messageRepository;
//    private final GroupChatRoomRepository roomRepository;
//    private final UserRepository userRepository;
//
//    public GroupChatService(GroupChatMessageRepository messageRepository,
//        GroupChatRoomRepository roomRepository,
//        UserRepository userRepository) {
//        this.messageRepository = messageRepository;
//        this.roomRepository = roomRepository;
//        this.userRepository = userRepository;
//    }
//
//    public GroupChatMessageResponseDto saveMessage(GroupChatMessageRequestDto requestDto) {
//        // 필요한 도메인 객체 조회
//        GroupChatRoom room = roomRepository.findById(requestDto.getRoomId())
//            .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
//        User sender = userRepository.findById(requestDto.getSenderId())
//            .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
//
//        // 메시지 생성 및 저장
//        GroupChatMessage message = new GroupChatMessage();
//        message.setRoom(room);
//        message.setSender(sender);
//        message.setContent(requestDto.getContent());
//        message.setSentAt(LocalDateTime.now());
//
//        messageRepository.save(message);
//
//        // 응답 DTO 구성
//        return new GroupChatMessageResponseDto(
//            message.getMessageId(),
//            message.getContent(),
//            sender.getNickname(),
//            sender.getProfileImageUrl(), // 프론트에서 필요
//            message.getSentAt()
//        );
//    }
//}
