package grep.neogul_coder.domain.recruitment.post.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class RecruitmentPostCommentPagingInfo {

    @Schema(example = "[ {nickname: 닉네임, imageUrl: www.., content: 댓글}, {nickname: 닉네임2, imageUrl: www.., content: 댓글2} ]")
    private List<CommentInfo> commentsInfos;

    @Schema(example = "3", description = "총 페이지 수")
    private int totalPage;

    @Schema(example = "20", description = "총 요소 개수")
    private int totalElementCount;

    @Getter
    static class CommentInfo {

        @Schema(example = "테스터", description = "닉네임")
        private String nickname;

        @Schema(example = "www.s3.com", description = "회원 이미지 접근 URL")
        private String imageUrl;

        @Schema(example = "참여 하고 싶습니다!", description = "댓글 내용")
        private String content;
    }
}
