package grep.neogul_coder.domain.studypost.controller.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import grep.neogul_coder.domain.studypost.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class PostInfo {

    @Schema(example = "3", description = "작성자 식별자")
    private long userId;

    @Schema(description = "작성자 닉네임", example = "너굴코더")
    private String nickname;

    @Schema(description = "작성자 프로필 이미지 URL", example = "https://cdn.example.com/profile.jpg")
    private String imageUrl;

    @Schema(description = "게시글 ID", example = "10")
    private Long postId;

    @Schema(description = "제목", example = "모든 국민은 직업선택의 자유를 가진다.")
    private String title;

    @Schema(description = "카테고리: NOTICE(공지), FREE(자유)", example = "NOTICE")
    private Category category;

    @Schema(description = "본문", example = "국회는 의원의 자격을 심사하며, 의원을 징계할 있다.")
    private String content;

    @Schema(description = "작성일", example = "2025-07-10T14:00:00")
    private LocalDateTime createdDate;

    @QueryProjection
    public PostInfo(long userId, String nickname, String imageUrl, Long postId,
                    String title, Category category, String content, LocalDateTime createdDate) {
        this.userId = userId;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.postId = postId;
        this.title = title;
        this.category = category;
        this.content = content;
        this.createdDate = createdDate;
    }
}
