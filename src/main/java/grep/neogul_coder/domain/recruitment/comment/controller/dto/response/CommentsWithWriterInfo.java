package grep.neogul_coder.domain.recruitment.comment.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@ToString
@Getter
public class CommentsWithWriterInfo {

    @Schema(example = "3", description = "회원 식별자")
    private Long userId;

    @Schema(example = "테스터", description = "닉네임")
    private String nickname;

    @Schema(example = "www.s3.com", description = "회원 이미지 접근 URL")
    private String imageUrl;

    @Schema(example = "3", description = "댓글 식별자")
    private long commentId;

    @Schema(example = "참여 하고 싶습니다!", description = "댓글 내용")
    private String content;

    @Schema(example = "2025-07-18", description = "댓글 생성일")
    private LocalDate createdAt;

    @JsonIgnore
    private boolean activated;

    @QueryProjection
    public CommentsWithWriterInfo(Long userId, String nickname, String imageUrl, long commentId,
                                  String content, LocalDateTime createdAt, boolean activated) {
        this.userId = userId;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.commentId = commentId;
        this.content = content;
        this.createdAt = createdAt.toLocalDate();
        this.activated = activated;
    }

    public CommentsWithWriterInfo updateNickName(String nickname) {
        return new CommentsWithWriterInfo(this.userId, nickname, this.imageUrl, this.commentId, this.content,
                LocalDateTime.of(this.createdAt, LocalTime.MIDNIGHT), this.activated);
    }
}
