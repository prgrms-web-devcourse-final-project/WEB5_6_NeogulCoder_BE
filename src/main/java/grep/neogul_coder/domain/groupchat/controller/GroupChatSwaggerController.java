package grep.neogul_coder.domain.groupchat.controller;

import grep.neogul_coder.domain.groupchat.controller.dto.requset.GroupChatSwaggerRequest;
import grep.neogul_coder.domain.groupchat.controller.dto.response.GroupChatSwaggerResponse;
import grep.neogul_coder.global.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/ws-stomp")
public class GroupChatSwaggerController implements GroupChatSwaggerSpecification {

    @PostMapping("/pub/chat/message")
    @Override
    public ApiResponse<GroupChatSwaggerResponse> sendMessage(@RequestBody GroupChatSwaggerRequest request) {
        GroupChatSwaggerResponse response = GroupChatSwaggerResponse.of(
            request.getSenderId(),
            "강현",
            "https://ganghyeon.jpg",
            request.getRoomId(),
            request.getMessage(),
            LocalDateTime.now()
        );
        return ApiResponse.success(response);
    }

    @GetMapping("/sub/chat/room/{roomId}")
    @Override
    public ApiResponse<List<GroupChatSwaggerResponse>> getMessages(@PathVariable Long roomId) {
        List<GroupChatSwaggerResponse> messages = List.of(
            GroupChatSwaggerResponse.of(1L, "강현", "https://ganghyeon.jpg", roomId, "안녕하세요!", LocalDateTime.now().minusMinutes(2)),
            GroupChatSwaggerResponse.of(2L, "강민", "https://gangmin.jpg", roomId, "반갑습니다!", LocalDateTime.now().minusMinutes(1))
        );
        return ApiResponse.success(messages);
    }
}
