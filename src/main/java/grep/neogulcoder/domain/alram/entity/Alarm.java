package grep.neogulcoder.domain.alram.entity;

import grep.neogulcoder.domain.alram.type.AlarmType;
import grep.neogulcoder.domain.alram.type.DomainType;
import grep.neogulcoder.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Schema(description = "알림 정보")
public class Alarm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long receiverUserId;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @Enumerated(EnumType.STRING)
    private DomainType domainType;

    private Long domainId;

    private String message;

    private boolean checked = false;

    public void checkAlarm() {
        this.checked = true;
    }

    public static Alarm init(AlarmType alarmType, Long receiverUserId , DomainType domainType, Long domainId, String message) {
        return Alarm.builder()
            .alarmType(alarmType)
            .receiverUserId(receiverUserId)
            .domainType(domainType)
            .domainId(domainId)
            .message(message)
            .build();
    }

    @Builder
    private Alarm(Long id, Long receiverUserId, AlarmType alarmType, DomainType domainType,
        Long domainId, String message) {
        this.id = id;
        this.receiverUserId = receiverUserId;
        this.alarmType = alarmType;
        this.domainType = domainType;
        this.domainId = domainId;
        this.message = message;
    }

    protected Alarm() {
    }

}
