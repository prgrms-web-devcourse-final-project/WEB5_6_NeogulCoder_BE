package grep.neogulcoder.domain.study.event;

public record StudyInviteEvent(Long studyId, Long inviterId, Long targetUserId) {

}
