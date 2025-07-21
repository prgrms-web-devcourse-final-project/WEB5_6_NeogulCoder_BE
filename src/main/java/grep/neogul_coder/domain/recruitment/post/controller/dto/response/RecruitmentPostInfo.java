package grep.neogul_coder.domain.recruitment.post.controller.dto.response;

import grep.neogul_coder.domain.recruitment.comment.controller.dto.response.CommentsWithWriterInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class RecruitmentPostInfo {

    @Schema(example = "{ category: IT, location: 서울, studyType: OFFLINE, startedDate: 2025-07-09, endDate: 2025-08-10... }")
    private RecruitmentPostWithStudyInfo postDetailsInfo;

    @Schema(example = "[ {nickname: 닉네임, imageUrl: www.., content: 댓글}, {nickname: 닉네임2, imageUrl: www.., content: 댓글2} ]")
    private List<CommentsWithWriterInfo> commentsWithWriterInfos;

    @Schema(example = "2", description = "신청 내역 수")
    private int applicationCount;

    @Schema(example = "3", description = "댓글 개수")
    private int commentCount;

    public RecruitmentPostInfo(RecruitmentPostWithStudyInfo postInfo, List<CommentsWithWriterInfo> comments, int applicationCount) {
        this.postDetailsInfo = postInfo;
        this.commentsWithWriterInfos = comments;
        this.applicationCount = applicationCount;
        this.commentCount = comments.size();
    }

}
