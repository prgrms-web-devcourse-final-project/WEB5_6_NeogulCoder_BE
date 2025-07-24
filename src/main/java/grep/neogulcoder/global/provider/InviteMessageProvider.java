package grep.neogulcoder.global.provider;

import grep.neogulcoder.domain.alram.type.AlarmType;
import org.springframework.stereotype.Component;

@Component
public class InviteMessageProvider implements MessageProvidable{

    @Override
    public boolean isSupport(AlarmType alarmType) {
        return alarmType == AlarmType.INVITE;
    }

    @Override
    public String provideMessage() {
        return "스터디에서 당신을 초대하고 싶어합니다.";
    }

}
