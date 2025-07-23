package grep.neogulcoder.domain.studypost.controller.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import grep.neogulcoder.domain.studypost.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class PostPagingInfo {

    @Schema(description = "게시글 ID", example = "12")
    private Long id;

    @Schema(description = "제목", example = "모든 국민은 직업선택의 자유를 가진다.")
    private String title;

    @Schema(description = "카테고리: NOTICE(공지), FREE(자유)", example = "NOTICE")
    private Category category;

    @Schema(description = "본문", example = "국회는 의원의 자격을 심사하며, 의원을 징계할 있다.")
    private String content;

    @Schema(description = "작성일", example = "2025-07-10T14:32:00")
    private LocalDateTime createdDate;

    @Schema(description = "댓글 수", example = "3")
    private long commentCount;

    @QueryProjection
    public PostPagingInfo(Long id, String title, Category category,
                          String content, LocalDateTime createdDate, long commentCount) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.content = content;
        this.createdDate = createdDate;
        this.commentCount = commentCount;
    }
}
