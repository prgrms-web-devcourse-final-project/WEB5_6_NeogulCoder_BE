package grep.neogulcoder.domain.studyapplication.event;

import grep.neogulcoder.domain.alram.type.AlarmType;

public record ApplicationStatusChangedEvent(Long applicationId, AlarmType alarmType) {
}
