package grep.neogulcoder.domain.studypost.comment.event;

import lombok.Getter;

@Getter
public class StudyPostCommentEvent {

    private long targetUserId;
    private long postId;

    public StudyPostCommentEvent(long targetUserId, long postId) {
        this.targetUserId = targetUserId;
        this.postId = postId;
    }
}
