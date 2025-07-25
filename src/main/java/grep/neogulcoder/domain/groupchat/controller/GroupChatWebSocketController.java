package grep.neogulcoder.domain.groupchat.controller;

import grep.neogulcoder.domain.groupchat.controller.dto.requset.GroupChatMessageRequestDto;
import grep.neogulcoder.domain.groupchat.controller.dto.response.GroupChatMessageResponseDto;
import grep.neogulcoder.domain.groupchat.service.GroupChatService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

// Swagger 문서에 노출되지 않도록 설정
@Hidden
@Slf4j
@Controller
public class GroupChatWebSocketController {

    private final GroupChatService groupChatService;
    private final SimpMessagingTemplate messagingTemplate;

    // 생성자 주입을 통해 필요한 서비스와 템플릿 객체 주입
    public GroupChatWebSocketController(GroupChatService groupChatService,
        SimpMessagingTemplate messagingTemplate) {
        this.groupChatService = groupChatService;
        this.messagingTemplate = messagingTemplate;
    }

    // 클라이언트가 /pub/chat/message 로 보낼 때 처리됨
    @MessageMapping("/chat/message")
    public void handleMessage(GroupChatMessageRequestDto requestDto) {
        log.info("[웹소켓] 새 메시지 수신 - 스터디: {}, 보낸 사람: {}, 내용: {}",
            requestDto.getStudyId(), requestDto.getSenderId(), requestDto.getMessage());

        // 메시지를 DB에 저장하고, 응답 DTO 생성
        GroupChatMessageResponseDto responseDto = groupChatService.saveMessage(requestDto);

        // 구독 중인 클라이언트에게 메시지 전송 (스터디 구분)
        // 클라이언트는 /sub/chat/study/{studyId} 구독 중이어야 실시간으로 수신 가능
        log.info("[웹소켓] 스터디 {} 구독자에게 메시지 전송 완료", requestDto.getStudyId());
        messagingTemplate.convertAndSend(
            "/sub/chat/study/" + requestDto.getStudyId(), // 메시지를 받을 대상
            responseDto                                          // 클라이언트에 전달할 응답 메시지 DTO
        );
    }
}
