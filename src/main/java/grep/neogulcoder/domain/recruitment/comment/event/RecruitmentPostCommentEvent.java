package grep.neogulcoder.domain.recruitment.comment.event;

import lombok.Getter;

@Getter
public class RecruitmentPostCommentEvent {

    private long targetUserId;
    private long postId;

    public RecruitmentPostCommentEvent(long targetUserId, long postId) {
        this.targetUserId = targetUserId;
        this.postId = postId;
    }
}
