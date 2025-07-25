package grep.neogulcoder.domain.groupchat.service;

import grep.neogulcoder.domain.groupchat.entity.GroupChatMessage;
import grep.neogulcoder.domain.groupchat.entity.GroupChatRoom;
import grep.neogulcoder.domain.groupchat.controller.dto.requset.GroupChatMessageRequestDto;
import grep.neogulcoder.domain.groupchat.controller.dto.response.GroupChatMessageResponseDto;
import grep.neogulcoder.domain.groupchat.repository.GroupChatMessageRepository;
import grep.neogulcoder.domain.groupchat.repository.GroupChatRoomRepository;
import grep.neogulcoder.domain.study.repository.StudyMemberRepository;
import grep.neogulcoder.domain.users.entity.User;
import grep.neogulcoder.domain.users.repository.UserRepository;
import grep.neogulcoder.global.response.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class GroupChatService {

    private final GroupChatMessageRepository messageRepository;
    private final GroupChatRoomRepository roomRepository;
    private final UserRepository userRepository;
    private final StudyMemberRepository studyMemberRepository;


    @Transactional
    public GroupChatMessageResponseDto saveMessage(GroupChatMessageRequestDto requestDto) {
        log.info("[서비스] 메시지 저장 시작 - 채팅방: {}, 보낸 사람: {}",
            requestDto.getRoomId(), requestDto.getSenderId());

        // 채팅방 존재 여부 확인
        GroupChatRoom room = roomRepository.findById(requestDto.getRoomId())
            .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
        // 메시지 발신자(사용자) 정보 조회
        User sender = userRepository.findById(requestDto.getSenderId())
            .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        log.info("[서비스] 보낸 사람 '{}' 검증 완료 (roomId={})", sender.getNickname(), room.getRoomId());

        // 스터디 참가자 검증 로직 추가
        Long studyId = room.getStudyId();
        boolean isParticipant = studyMemberRepository.existsByStudyIdAndUserId(studyId, sender.getId());
        if (!isParticipant) {
            throw new IllegalArgumentException("해당 스터디에 참가한 사용자만 채팅할 수 있습니다.");
        }

        // 메시지 생성
        GroupChatMessage message = requestDto.toEntity(room, sender.getId());
        // 메시지 저장
        messageRepository.save(message);

        log.info("[서비스] 메시지 저장 완료 - 메시지ID: {}, 채팅방: {}, 시간: {}",
            message.getMessageId(), room.getRoomId(), message.getSentAt());

        // 응답 dto 생성
        return GroupChatMessageResponseDto.from(message, sender);
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

            return GroupChatMessageResponseDto.from(message, sender);
        });

        // PageResponse로 감싸서 반환
        return new PageResponse<>(
            "/api/chat/room/" + roomId + "/messages",
            messagePage,
            5 // 페이지 버튼 개수
        );
    }


}
