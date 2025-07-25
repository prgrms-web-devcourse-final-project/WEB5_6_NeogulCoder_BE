package grep.neogulcoder.global.provider;

import grep.neogulcoder.domain.alram.type.AlarmType;
import grep.neogulcoder.domain.alram.type.DomainType;

public interface MessageProvidable {

    boolean isSupport(AlarmType alarmType);

    String provideMessage(DomainType domainType, Long domainId);
}
