package grep.neogul_coder.domain.recruitment.comment.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CommentsWithWriterInfo {

    @Schema(example = "테스터", description = "닉네임")
    private String nickname;

    @Schema(example = "www.s3.com", description = "회원 이미지 접근 URL")
    private String imageUrl;

    @Schema(example = "참여 하고 싶습니다!", description = "댓글 내용")
    private String content;

    @JsonIgnore
    private Long userId;

    @JsonIgnore
    private boolean activated;

    @QueryProjection
    public CommentsWithWriterInfo(Long userId, String nickname, String imageUrl,
                                  String content, boolean activated) {
        this.userId = userId;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.content = content;
        this.activated = activated;
    }

    public CommentsWithWriterInfo updateNickName(String nickname) {
        return new CommentsWithWriterInfo(this.userId, nickname, this.imageUrl, this.content, this.activated);
    }
}
