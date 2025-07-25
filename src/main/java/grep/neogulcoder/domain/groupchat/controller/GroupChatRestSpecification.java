package grep.neogulcoder.domain.groupchat.controller;

import grep.neogulcoder.domain.groupchat.controller.dto.response.GroupChatMessageResponseDto;
import grep.neogulcoder.global.response.ApiResponse;
import grep.neogulcoder.global.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "GroupChat", description = "채팅 메시지 조회 API (무한 스크롤용)")
public interface GroupChatRestSpecification {

    @Operation(
        summary = "채팅 메시지 페이징 조회",
        description = """
        이 API는 **채팅방의 과거 메시지**를 페이지 단위로 가져오는 용도입니다.  
        WebSocket의 실시간 수신(`/sub/chat/study/{studyId}`)과는 별개로,  
        채팅방에 입장할 때 이전 대화 기록을 불러오는 데 사용됩니다.
        
        ---
        
        **프론트엔드 연동 흐름 (권장 방식)**:
        1. **채팅방 입장 시** → `GET /api/chat/study/{studyId}/messages?page=0&size=20` 호출해 최신 메시지 20개 로드  
        2. **스크롤 위로 올릴 때** → `page=1`, `page=2` ... 순차적으로 과거 메시지를 추가 로딩 (무한 스크롤)  
        3. **동시에** → WebSocket(`wss://wibby.cedartodo.uk/ws-stomp`) 연결 후 `/sub/chat/study/{studyId}`를 **구독**해 실시간 메시지 수신  
        
        ---
        
        **파라미터 설명**:
        - `studyId`: 스터디 ID  
        - `page`: 페이지 번호 (0부터 시작, 0 = 최신 메시지 20개)  
        - `size`: 한 페이지당 메시지 수 (기본값 20)  
        - 메시지는 **오래된 순(오름차순)**으로 반환됩니다.
        
        ---
        
        **응답 구조**:
        - `ApiResponse<PageResponse<GroupChatMessageResponseDto>>` 형태  
        - `content()`로 메시지 목록 접근 가능  
        - 페이지네이션 정보: `currentNumber()`, `prevPage()`, `nextPage()` 등
        
        ---
        
        **예시 요청 URL**:
        ```
        /api/chat/study/1/messages?page=0&size=20
        ```
        
        **예시 응답**:
        ```json
        {
          "success": true,
          "data": {
            "content": [
              {
                "id": 101,
                "studyId": 1,
                "senderId": 10,
                "senderNickname": "유강현",
                "profileImageUrl": "https://example.com/profile.jpg",
                "message": "안녕하세요!",
                "sentAt": "2025-07-21T14:32:00"
              }
            ],
            "currentNumber": 0,
            "nextPage": 1,
            "prevPage": null
          }
        }
        ```
        """
    )

    ApiResponse<PageResponse<GroupChatMessageResponseDto>> getMessages(
        @Parameter(description = "스터디 ID", example = "1")
        @PathVariable("studyId") Long studyId,

        @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
        @RequestParam(defaultValue = "0") int page,

        @Parameter(description = "한 페이지당 메시지 수", example = "20")
        @RequestParam(defaultValue = "20") int size
    );
}
