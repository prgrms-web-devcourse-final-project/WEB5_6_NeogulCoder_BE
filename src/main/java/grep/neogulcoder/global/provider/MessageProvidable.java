package grep.neogulcoder.global.provider;

import grep.neogulcoder.domain.alram.type.AlarmType;

public interface MessageProvidable {
    boolean isSupport(AlarmType alarmType);
    String provideMessage();
}
