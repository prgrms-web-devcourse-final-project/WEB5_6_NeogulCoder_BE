package grep.neogul_coder.domain.studypost.controller.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class CommentInfo {

    @Schema(description = "댓글 ID", example = "3")
    private long userId;

    @Schema(description = "작성자 닉네임", example = "너굴코더")
    private String nickname;

    @Schema(description = "작성자 프로필 이미지 URL", example = "https://cdn.example.com/profile.jpg")
    private String profileImageUrl;

    @Schema(description = "댓글 ID", example = "100")
    private long id;

    @Schema(description = "댓글 내용", example = "정말 좋은 정보 감사합니다!")
    private String content;

    @Schema(description = "작성일", example = "2025-07-10T14:45:00")
    private LocalDateTime createdAt;

    @QueryProjection
    public CommentInfo(long userId, String nickname, String profileImageUrl, long id,
                       String content, LocalDateTime createdAt) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
    }
}
