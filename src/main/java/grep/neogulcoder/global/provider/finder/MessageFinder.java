package grep.neogulcoder.global.provider.finder;

import grep.neogulcoder.domain.alram.type.AlarmType;
import grep.neogulcoder.domain.alram.type.DomainType;
import grep.neogulcoder.global.provider.MessageProvidable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageFinder {

    private final List<MessageProvidable> providers;

    public String findMessage(AlarmType alarmType, DomainType domainType, Long domainId) {
        return providers.stream()
            .filter(provider -> provider.isSupport(alarmType))
            .findFirst()
            .map(provider -> provider.provideMessage(domainType, domainId))
            .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 알림 타입입니다."));
    }

}
