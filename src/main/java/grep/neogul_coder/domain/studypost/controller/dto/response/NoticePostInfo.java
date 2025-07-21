package grep.neogul_coder.domain.studypost.controller.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import grep.neogul_coder.domain.studypost.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class NoticePostInfo {

    @Schema(example = "3", description = "게시글 ID")
    private long postId;

    @Schema(example = "공지", description = "게시글 타입")
    private String category;

    @Schema(example = "제목", description = "공지글 제목")
    private String title;

    @Schema(example = "2025-07-21", description = "생성일")
    private LocalDate createdAt;

    @QueryProjection
    public NoticePostInfo(long postId, Category category, String title, LocalDateTime createdAt) {
        this.postId = postId;
        this.category = category.getKorean();
        this.title = title;
        this.createdAt = createdAt.toLocalDate();
    }
}
