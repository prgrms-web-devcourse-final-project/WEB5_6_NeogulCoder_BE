package grep.neogulcoder.domain.groupchat.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class ChatMessagePagingResponse {

    @Schema(description = "채팅 메시지 목록")
    private final List<GroupChatMessageResponseDto> content;

    @Schema(description = "현재 페이지 번호", example = "0")
    private final int currentPage;

    @Schema(description = "페이지 크기", example = "20")
    private final int size;

    @Schema(description = "전체 페이지 수", example = "5")
    private final int totalPages;

    @Schema(description = "전체 메시지 수", example = "100")
    private final long totalElements;

    @Schema(description = "다음 페이지 존재 여부", example = "true")
    private final boolean hasNext;

    @Builder
    private ChatMessagePagingResponse(Page<GroupChatMessageResponseDto> page) {
        this.content = page.getContent();
        this.currentPage = page.getNumber();
        this.size = page.getSize();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.hasNext = page.hasNext();
    }

    public static ChatMessagePagingResponse of(Page<GroupChatMessageResponseDto> page) {
        return new ChatMessagePagingResponse(page);
    }
}
