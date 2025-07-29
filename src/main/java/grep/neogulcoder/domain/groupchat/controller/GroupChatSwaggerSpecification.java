package grep.neogulcoder.domain.groupchat.controller;

import grep.neogulcoder.domain.groupchat.controller.dto.request.GroupChatSwaggerRequest;
import grep.neogulcoder.domain.groupchat.controller.dto.response.GroupChatSwaggerResponse;
import grep.neogulcoder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import org.springframework.http.ResponseEntity;

@Tag(name = "GroupChat", description = "WebSocket 구조 설명용 Swagger 문서")
public interface GroupChatSwaggerSpecification {

    @Operation(summary = "채팅 메시지 전송 (WebSocket Pub)",
        description = """
        **실제 채팅 메시지 전송은 WebSocket 연결 후에 이루어집니다.**

        **1. WebSocket 연결**
        - 먼저 `wss://wibby.cedartodo.uk/ws-stomp` 엔드포인트로 STOMP 연결을 맺습니다.

        **2. 메시지 전송**
        - 연결이 완료된 후 `/pub/chat/message` 경로로 메시지를 보냅니다.

        **예시 Request JSON**
        ```json
        {
          "studyId": 1,
          "senderId": 10,
          "message": "안녕하세요!"
        }
        ```

        ** Swagger에서 이 API를 실행해도 실제 전송은 되지 않으며, 
        WebSocket 통신 구조를 이해하기 위한 문서 예시입니다.**
        """)
    ResponseEntity<ApiResponse<GroupChatSwaggerResponse>> sendMessage(GroupChatSwaggerRequest request);

    @Operation(summary = "채팅 메시지 수신 (WebSocket Sub)",
        description = """
        **실제 메시지 수신 또한 WebSocket 연결이 필수입니다.**

        **1. WebSocket 연결**
        - 먼저 `wss://wibby.cedartodo.uk/ws-stomp` 엔드포인트로 STOMP 연결을 맺습니다.

        **2. 메시지 구독**
        - 연결 후 `/sub/chat/study/{studyId}` 경로를 구독(subscribe)하면 해당 채팅방의 새로운 메시지를 실시간으로 수신할 수 있습니다.

        **예시 Subscribe 경로**
        `/sub/chat/study/1`

        **예시 수신 데이터(JSON)**
        ```json
        {
          "id": 101,
          "studyId": 1,
          "senderId": 10,
          "senderNickname": "유강현",
          "profileImageUrl": "https://example.com/profile.jpg",
          "message": "안녕하세요!",
          "sentAt": "2025-07-21T14:32:00"
        }
        ```

        ** Swagger에서는 WebSocket 구독을 테스트할 수 없으며,
        이 문서는 프론트엔드 구현 참고용입니다.**
        """)
    ResponseEntity<ApiResponse<List<GroupChatSwaggerResponse>>> getMessages(Long studyId);
}
