package grep.neogul_coder.domain.groupchat.controller;

import grep.neogul_coder.domain.groupchat.controller.dto.GroupChatSwaggerRequest;
import grep.neogul_coder.domain.groupchat.controller.dto.GroupChatSwaggerResponse;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "GroupChat-Swagger", description = "WebSocket 구조 설명용 Swagger 문서")
public interface GroupChatSwaggerSpecification {

    @Operation(summary = "Pub 채팅 메시지 전송 구조 예시", description = "실제 채팅 전송은 WebSocket을 통해 `/pub/chat/message`로 전송됩니다.")
    ApiResponse<GroupChatSwaggerResponse> sendMessage(GroupChatSwaggerRequest request);

    @Operation(summary = "Sub 채팅 메시지 수신 구조 예시", description = "실제 메시지 수신은 WebSocket을 통해 `/sub/chat/room/{roomId}`로 수신됩니다.")
    ApiResponse<List<GroupChatSwaggerResponse>> getMessages(Long roomId);
}
