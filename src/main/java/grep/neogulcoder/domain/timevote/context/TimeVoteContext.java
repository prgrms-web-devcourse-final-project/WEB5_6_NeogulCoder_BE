package grep.neogulcoder.domain.timevote.context;

import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.timevote.TimeVotePeriod;

public record TimeVoteContext(
    StudyMember studyMember,
    TimeVotePeriod period
) {}