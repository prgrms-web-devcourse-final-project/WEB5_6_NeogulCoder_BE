package grep.neogulcoder.domain.recruitment.comment.controller.dto.request;

import grep.neogulcoder.domain.recruitment.comment.RecruitmentPostComment;
import grep.neogulcoder.domain.recruitment.post.RecruitmentPost;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class RecruitmentCommentSaveRequest {

    @Schema(example = "저도 참여 할래요!", description = "모집글 내용")
    private String content;

    private RecruitmentCommentSaveRequest() {
    }

    public RecruitmentCommentSaveRequest(String content) {
        this.content = content;
    }

    public RecruitmentPostComment toEntity(RecruitmentPost post, long userId) {
        return RecruitmentPostComment.builder()
                .recruitmentPost(post)
                .userId(userId)
                .content(this.content)
                .build();
    }

}
