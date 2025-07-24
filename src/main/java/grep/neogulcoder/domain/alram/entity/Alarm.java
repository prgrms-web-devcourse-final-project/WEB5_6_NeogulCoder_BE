package grep.neogulcoder.domain.alram.entity;

import grep.neogulcoder.domain.alram.type.AlarmType;
import grep.neogulcoder.domain.alram.type.DomainType;
import grep.neogulcoder.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Alarm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long receiverUserId;

    private AlarmType alarmType;

    private DomainType domainType;

    private Long domainId;

    private String message;

    private boolean isRead = false;

    public void read() {
        this.isRead = true;
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
