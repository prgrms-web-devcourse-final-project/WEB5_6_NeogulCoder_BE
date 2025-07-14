package grep.neogul_coder.domain.groupchat.controller;

import grep.neogul_coder.domain.groupchat.controller.dto.requset.GroupChatMessageRequestDto;
import grep.neogul_coder.domain.groupchat.controller.dto.response.GroupChatMessageResponseDto;
import grep.neogul_coder.domain.groupchat.service.GroupChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GroupChatWebSocketController {

    private final GroupChatService groupChatService;
    private final SimpMessagingTemplate messagingTemplate;

    public GroupChatWebSocketController(GroupChatService groupChatService,
        SimpMessagingTemplate messagingTemplate) {
        this.groupChatService = groupChatService;
        this.messagingTemplate = messagingTemplate;
    }

    // 클라이언트가 /pub/chat/message 로 보낼 때 처리됨
    @MessageMapping("/chat/message")
    public void handleMessage(GroupChatMessageRequestDto requestDto) {
        // 메시지를 DB에 저장하고, 응답 DTO 생성
        GroupChatMessageResponseDto responseDto = groupChatService.saveMessage(requestDto);

        // 구독 중인 클라이언트에게 메시지 전송 (채팅방 구분)
        messagingTemplate.convertAndSend(
            "/sub/chat/room/" + requestDto.getRoomId(), responseDto
        );
    }
}
