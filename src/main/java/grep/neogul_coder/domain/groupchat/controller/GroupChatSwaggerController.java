package grep.neogul_coder.domain.groupchat.controller;

import grep.neogul_coder.domain.groupchat.dto.GroupChatSwaggerRequest;
import grep.neogul_coder.domain.groupchat.dto.GroupChatSwaggerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/swagger/groupchat")
public class GroupChatSwaggerController {

    @Operation(summary = "채팅 메시지 보내기 (Swagger용)", description = "WebSocket 송신 메시지 구조 예시")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "전송 성공",
            content = @Content(schema = @Schema(implementation = GroupChatSwaggerResponse.class)))
    })
    @PostMapping
    public ResponseEntity<GroupChatSwaggerResponse> sendMessage(
        @RequestBody GroupChatSwaggerRequest request
    ) {
        GroupChatSwaggerResponse response = GroupChatSwaggerResponse.of(
            request.getSenderId(),
            "강현",
            "https://cdn.example.com/avatar/ganghyeon.jpg",
            request.getRoomId(),
            request.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "채팅 메시지 수신 예시", description = "WebSocket 수신 메시지 구조 예시 (Swagger에서 구조 확인용)")
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<GroupChatSwaggerResponse>> getMessages(@PathVariable Long roomId) {
        List<GroupChatSwaggerResponse> messages = List.of(
            GroupChatSwaggerResponse.of(1L, "강현", "https://ganghyeon.jpg", roomId, "안녕하세요!", LocalDateTime.now().minusMinutes(2)),
            GroupChatSwaggerResponse.of(2L, "강민", "https://gangmin.jpg", roomId, "반갑습니다!", LocalDateTime.now().minusMinutes(1))
        );
        return ResponseEntity.ok(messages);
    }
}
