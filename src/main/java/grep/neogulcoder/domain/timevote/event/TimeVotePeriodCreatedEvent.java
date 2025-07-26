package grep.neogulcoder.domain.timevote.event;

public record TimeVotePeriodCreatedEvent(
    Long studyId,
    Long excludedUserId
) {}
