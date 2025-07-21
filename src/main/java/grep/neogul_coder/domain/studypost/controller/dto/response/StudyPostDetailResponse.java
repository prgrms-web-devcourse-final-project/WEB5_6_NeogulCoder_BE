package grep.neogul_coder.domain.studypost.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Schema(description = "스터디 게시글 상세 응답 DTO")
public class StudyPostDetailResponse {

    @Schema(description = "게시글 회원 정보")
    private PostInfo postInfo;

    @Schema(description = "댓글 목록")
    private List<CommentInfo> comments;

    @Schema(description = "댓글 수", example = "3")
    private int commentCount;

    public StudyPostDetailResponse(PostInfo postInfo, List<CommentInfo> comments, int commentCount) {
        this.postInfo = postInfo;
        this.comments = comments;
        this.commentCount = commentCount;
    }
}
