package grep.neogulcoder.domain.groupchat.controller;

import grep.neogulcoder.domain.groupchat.controller.dto.requset.GroupChatSwaggerRequest;
import grep.neogulcoder.domain.groupchat.controller.dto.response.GroupChatSwaggerResponse;
import grep.neogulcoder.global.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ws-stomp")
public class GroupChatSwaggerController implements GroupChatSwaggerSpecification {


    @PostMapping("/pub/chat/message")
    @Override
    public ApiResponse<GroupChatSwaggerResponse> sendMessage(
        @RequestBody GroupChatSwaggerRequest request) {
        return ApiResponse.success(new GroupChatSwaggerResponse());
    }


    @GetMapping("/sub/chat/room/{roomId}")
    @Override
    public ApiResponse<List<GroupChatSwaggerResponse>> getMessages(@PathVariable Long roomId) {
        List<GroupChatSwaggerResponse> messages = List.of(
            new GroupChatSwaggerResponse(),
            new GroupChatSwaggerResponse()
        );
        return ApiResponse.success(messages);
    }
}
