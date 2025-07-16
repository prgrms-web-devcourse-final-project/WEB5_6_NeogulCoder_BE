package grep.neogul_coder.domain.groupchat.controller;

import grep.neogul_coder.domain.groupchat.controller.dto.response.GroupChatMessageResponseDto;
import grep.neogul_coder.global.response.ApiResponse;
import grep.neogul_coder.global.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "GroupChat-REST", description = "채팅 메시지 조회 API (무한 스크롤용)")
public interface GroupChatRestSpecification {

    @Operation(
        summary = "채팅 메시지 페이징 조회",
        description = """
        채팅방의 과거 메시지를 페이지 단위로 조회합니다. <br>
        프론트는 무한 스크롤 방식으로 이 API를 반복 호출하여 이전 메시지를 계속 불러올 수 있습니다. <br><br>

        이 API는 WebSocket의 `/sub/chat/room/{roomId}` 실시간 수신과는 별개입니다. <br>
        사용자는 채팅방 입장 시 이 API로 과거 메시지를 먼저 가져오고 <br>
        이후 WebSocket을 연결해 실시간 메시지를 받는 방식으로 연동합니다. <br><br>

        전반적인 프론트 흐름은 다음과 같습니다: <br>
        1. 채팅방에 처음 입장하면 → 이 API로 `page=0`부터 메시지를 조회 <br>
        2. 이후 스크롤을 올릴 때마다 → `page=1`, `page=2`… 순차 호출 <br>
        3. 동시에 WebSocket `/sub/chat/room/{roomId}` 구독 시작 <br><br>

        파라미터 설명: <br>
        - `roomId`: 채팅방 식별자입니다. <br>
        - `page`: 0부터 시작하는 페이지 번호입니다. (0번이 가장 오래된 메시지입니다.) <br>
        - `size`: 한 페이지에 포함시킬 메시지 개수입니다. <br>
        - 메시지는 **오래된 순(오름차순)** 으로 정렬되어 반환됩니다. <br>
        - 프론트는 최신 메시지를 아래에 배치하고, 스크롤을 위로 올릴 때마다 과거 메시지를 불러오도록 구현합니다. <br><br>
        

        응답 구조: <br>
        - `ApiResponse<PageResponse<...>>` 형태로 감싸져 있습니다. <br>
        - 실제 메시지는 `content()` 메서드를 통해 꺼낼 수 있습니다. <br>
        - 페이지네이션 정보는 `currentNumber()`, `prevPage()`, `nextPage()` 등을 통해 확인할 수 있습니다. <br><br>

        예시 요청 URL: `/api/chat/room/1/messages?page=0&size=20`
    """
    )

    ApiResponse<PageResponse<GroupChatMessageResponseDto>> getMessages(
        @Parameter(description = "채팅방 ID", example = "1")
        @PathVariable("roomId") Long roomId,

        @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
        @RequestParam(defaultValue = "0") int page,

        @Parameter(description = "한 페이지당 메시지 수", example = "20")
        @RequestParam(defaultValue = "20") int size
    );
}
