package grep.neogulcoder.domain.groupchat.controller;

import grep.neogulcoder.domain.groupchat.controller.dto.request.GroupChatSwaggerRequest;
import grep.neogulcoder.domain.groupchat.controller.dto.response.GroupChatSwaggerResponse;
import grep.neogulcoder.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ws-stomp")
public class GroupChatSwaggerController implements GroupChatSwaggerSpecification {


    @PostMapping("/pub/chat/message")
    @Override
    public ResponseEntity<ApiResponse<GroupChatSwaggerResponse>> sendMessage(
        @RequestBody GroupChatSwaggerRequest request) {
        return ResponseEntity.ok(ApiResponse.success(new GroupChatSwaggerResponse()));
    }


    @GetMapping("/sub/chat/study/{studyId}")
    @Override
    public ResponseEntity<ApiResponse<List<GroupChatSwaggerResponse>>> getMessages(@PathVariable Long studyId) {
        List<GroupChatSwaggerResponse> messages = List.of(
            new GroupChatSwaggerResponse(),
            new GroupChatSwaggerResponse()
        );
        return ResponseEntity.ok(ApiResponse.success(messages));
    }
}
