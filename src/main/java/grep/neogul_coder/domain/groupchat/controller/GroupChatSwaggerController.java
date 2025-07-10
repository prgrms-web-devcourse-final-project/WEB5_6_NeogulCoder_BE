package grep.neogul_coder.domain.groupchat.controller;

import grep.neogul_coder.domain.groupchat.controller.dto.GroupChatSwaggerRequest;
import grep.neogul_coder.domain.groupchat.controller.dto.GroupChatSwaggerResponse;
import grep.neogul_coder.global.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/ws-stomp")
public class GroupChatSwaggerController implements GroupChatSwaggerSpecification {


    @PostMapping("/pub/chat/message")
    @Override
    public ApiResponse<GroupChatSwaggerResponse> sendMessage(
        @RequestBody GroupChatSwaggerRequest request) {
        GroupChatSwaggerResponse response = GroupChatSwaggerResponse.builder().build();
        return ApiResponse.success(response);
    }


    @GetMapping("/sub/chat/room/{roomId}")
    @Override
    public ApiResponse<List<GroupChatSwaggerResponse>> getMessages(@PathVariable Long roomId) {
        List<GroupChatSwaggerResponse> messages = List.of(
            GroupChatSwaggerResponse.builder().build(),
            GroupChatSwaggerResponse.builder().build()
        );
        return ApiResponse.success(messages);
    }
}
