package grep.neogul_coder.domain.groupchat.controller;

import grep.neogul_coder.domain.groupchat.controller.dto.GroupChatSwaggerRequest;
import grep.neogul_coder.domain.groupchat.controller.dto.GroupChatSwaggerResponse;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/*
 이 컨트롤러는 Swagger에서 WebSocket 메시지 구조를 시각적으로 보여주기 위한 Mock 컨트롤러입니다!
 실제 채팅 기능은 WebSocket 기반 pub/sub 방식으로 동작하며,
   - 메시지 전송: WebSocket → /pub/chat/message (STOMP SEND)
   - 메시지 수신: WebSocket → /sub/chat/room/{roomId} (STOMP SUBSCRIBE)
 이 컨트롤러는 실행 용도가 아니라 응답 구조 파악용 Swagger 문서 생성을 목적으로 합니다!
 */
@RestController
@RequestMapping("/ws-stomp")
@Tag(name = "Swagger용 WebSocket 채팅 구조 예시", description = "WebSocket 메시지 구조를 설명용으로 보여줍니다.")
public class GroupChatSwaggerController {

    @Operation(summary = "Pub 채팅 메시지 전송 구조 예시", description = "실제 채팅 전송은 WebSocket을 통해 `/pub/chat/message`로 전송됩니다.")
    @PostMapping("/pub/chat/message")
    public ResponseEntity<ApiResponse<GroupChatSwaggerResponse>> sendMessage(
        @RequestBody GroupChatSwaggerRequest request
    ) {
        GroupChatSwaggerResponse response = GroupChatSwaggerResponse.of(
            request.getSenderId(),
            "강현",
            "https://ganghyeon.jpg",
            request.getRoomId(),
            request.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "Sub 채팅 메시지 수신 구조 예시", description = "실제 메시지 수신은 WebSocket을 통해 `/sub/chat/room/{roomId}`로 수신됩니다.")
    @GetMapping("/sub/chat/room/{roomId}")
    public ResponseEntity<ApiResponse<List<GroupChatSwaggerResponse>>> getMessages(
        @PathVariable Long roomId
    ) {
        List<GroupChatSwaggerResponse> messages = List.of(
            GroupChatSwaggerResponse.of(1L, "강현", "https://ganghyeon.jpg", roomId, "안녕하세요!", LocalDateTime.now().minusMinutes(2)),
            GroupChatSwaggerResponse.of(2L, "강민", "https://gangmin.jpg", roomId, "반갑습니다!", LocalDateTime.now().minusMinutes(1))
        );
        return ResponseEntity.ok(ApiResponse.success(messages));
    }
}
