package grep.neogulcoder.domain.groupchat.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@Schema(description = "Swagger용 채팅 메시지 응답 DTO")
public class GroupChatSwaggerResponse {

    @Schema(description = "메시지 고유 ID", example = "101")
    private Long id;

    @Schema(description = "보낸 사람 ID", example = "456")
    private Long senderId;

    @Schema(description = "보낸 사람 닉네임", example = "강현")
    private String senderNickname;

    @Schema(description = "프로필 이미지 URL", example = "https://ganghyeon.jpg")
    private String profileImageUrl;

    @Schema(description = "스터디 ID", example = "100")
    private Long studyId;

    @Schema(description = "보낸 메시지", example = "안녕하세요!")
    private String message;

    @Schema(description = "보낸 시간", example = "2025-07-07T17:45:00")
    private LocalDateTime sentAt;
}
