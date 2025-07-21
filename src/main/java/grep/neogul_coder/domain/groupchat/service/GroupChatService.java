package grep.neogul_coder.domain.groupchat.service;

import grep.neogul_coder.domain.groupchat.entity.GroupChatMessage;
import grep.neogul_coder.domain.groupchat.entity.GroupChatRoom;
import grep.neogul_coder.domain.groupchat.controller.dto.requset.GroupChatMessageRequestDto;
import grep.neogul_coder.domain.groupchat.controller.dto.response.GroupChatMessageResponseDto;
import grep.neogul_coder.domain.groupchat.repository.GroupChatMessageRepository;
import grep.neogul_coder.domain.groupchat.repository.GroupChatRoomRepository;
import grep.neogul_coder.domain.study.repository.StudyMemberRepository;
import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.domain.users.repository.UserRepository;
import grep.neogul_coder.global.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class GroupChatService {

    private final GroupChatMessageRepository messageRepository;
    private final GroupChatRoomRepository roomRepository;
    private final UserRepository userRepository;
    private final StudyMemberRepository studyMemberRepository;

    // 생성자 주입을 통한 의존성 주입
    public GroupChatService(GroupChatMessageRepository messageRepository,
        GroupChatRoomRepository roomRepository,
        UserRepository userRepository,
        StudyMemberRepository studyMemberRepository) {
        this.messageRepository = messageRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.studyMemberRepository = studyMemberRepository;
    }

    public GroupChatMessageResponseDto saveMessage(GroupChatMessageRequestDto requestDto) {
        // 채팅방 존재 여부 확인
        GroupChatRoom room = roomRepository.findById(requestDto.getRoomId())
            .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
        // 메시지 발신자(사용자) 정보 조회
        User sender = userRepository.findById(requestDto.getSenderId())
            .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        // 스터디 참가자 검증 로직 추가
        Long studyId = room.getStudyId();
        boolean isParticipant = studyMemberRepository.existsByStudyIdAndUserId(studyId, sender.getId());
        if (!isParticipant) {
            throw new IllegalArgumentException("해당 스터디에 참가한 사용자만 채팅할 수 있습니다.");
        }

        // 메시지 생성 및 저장
        GroupChatMessage message = new GroupChatMessage();
        message.setGroupChatRoom(room);                      // 엔티티에 있는 필드명 맞게 사용
        message.setUserId(sender.getId());                   // 연관관계 없이 userId만 저장
        message.setMessage(requestDto.getMessage());
        message.setSentAt(LocalDateTime.now());

        // 메시지 저장
        messageRepository.save(message);

        // 저장된 메시지를 dto로 변환
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

    // 과거 채팅 메시지 페이징 조회 (무한 스크롤용)
    public PageResponse<GroupChatMessageResponseDto> getMessages(Long roomId, int page, int size) {
        // 오래된 메시지부터 조회되도록 정렬 기준을 ASC로 설정
        Pageable pageable = PageRequest.of(page, size, Sort.by("sentAt").ascending());

        // JPQL 쿼리 메서드 기반 조회
        Page<GroupChatMessage> messages = messageRepository.findMessagesByRoomIdAsc(roomId, pageable);

        // 응답 DTO로 변환
        Page<GroupChatMessageResponseDto> messagePage = messages.map(message -> {
            User sender = userRepository.findById(message.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

            return new GroupChatMessageResponseDto(
                message.getMessageId(),
                message.getGroupChatRoom().getRoomId(),
                sender.getId(),
                sender.getNickname(),
                sender.getProfileImageUrl(),
                message.getMessage(),
                message.getSentAt()
            );
        });

        // PageResponse로 감싸서 반환
        return new PageResponse<>(
            "/api/chat/room/" + roomId + "/messages",
            messagePage,
            5 // 페이지 버튼 개수
        );
    }


}
