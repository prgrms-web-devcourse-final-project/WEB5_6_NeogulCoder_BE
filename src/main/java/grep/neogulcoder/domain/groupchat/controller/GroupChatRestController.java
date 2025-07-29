package grep.neogulcoder.domain.groupchat.controller;

import grep.neogulcoder.domain.groupchat.controller.dto.response.ChatMessagePagingResponse;
import grep.neogulcoder.domain.groupchat.controller.dto.response.GroupChatMessageResponseDto;
import grep.neogulcoder.domain.groupchat.service.GroupChatService;
import grep.neogulcoder.global.response.ApiResponse;
import grep.neogulcoder.global.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class GroupChatRestController implements GroupChatRestSpecification {

    private final GroupChatService groupChatService;

    // 과거 채팅 메시지 페이징 조회 (무한 스크롤용)
    @Override
    @GetMapping("/study/{studyId}/messages")
    public ResponseEntity<ApiResponse<ChatMessagePagingResponse>> getMessages(
        @PathVariable("studyId") Long studyId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        // 서비스에서 페이징된 메시지 조회
        ChatMessagePagingResponse response = groupChatService.getMessages(studyId, page, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
