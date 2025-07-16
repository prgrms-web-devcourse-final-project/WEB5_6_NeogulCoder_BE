package grep.neogul_coder.domain.groupchat.controller;

import grep.neogul_coder.domain.groupchat.controller.dto.response.GroupChatMessageResponseDto;
import grep.neogul_coder.domain.groupchat.service.GroupChatService;
import grep.neogul_coder.global.response.ApiResponse;
import grep.neogul_coder.global.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class GroupChatRestController implements GroupChatRestSpecification {

    private final GroupChatService groupChatService;

    // 과거 채팅 메시지 페이징 조회 (무한 스크롤용)
    @Override
    @GetMapping("/room/{roomId}/messages")
    public ApiResponse<PageResponse<GroupChatMessageResponseDto>> getMessages(
        @PathVariable("roomId") Long roomId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        // 서비스에서 페이징된 메시지 조회
        PageResponse<GroupChatMessageResponseDto> pageResponse =
            groupChatService.getMessages(roomId, page, size);

        // 공통 응답 형식에 맞게 반환
        return ApiResponse.success(pageResponse);
    }
}
