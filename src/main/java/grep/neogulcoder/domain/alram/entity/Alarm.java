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
    @Schema(description = "알림 ID", example = "1")
    private Long id;

    @Schema(description = "알림 수신자 유저 ID", example = "42")
    private Long receiverUserId;

    @Enumerated(EnumType.STRING)
    @Schema(description = "알림 타입", example = "COMMENT")
    private AlarmType alarmType;

    @Enumerated(EnumType.STRING)
    @Schema(description = "알림이 연관된 도메인 타입", example = "POST")
    private DomainType domainType;

    @Schema(description = "알림이 연관된 도메인 ID", example = "1001")
    private Long domainId;

    @Schema(description = "알림 메시지", example = "새로운 댓글이 달렸습니다.")
    private String message;

    @Schema(description = "알림 읽음 여부", example = "false")
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
