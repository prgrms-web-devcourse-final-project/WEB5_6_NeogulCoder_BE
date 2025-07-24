package grep.neogulcoder.domain.alram.controller.dto.response;

import grep.neogulcoder.domain.alram.entity.Alarm;
import grep.neogulcoder.domain.alram.type.AlarmType;
import grep.neogulcoder.domain.alram.type.DomainType;
import lombok.Builder;
import lombok.Data;

@Data
public class AlarmResponse {

    private Long id;

    private Long receiverUserId;

    private AlarmType alarmType;

    private DomainType domainType;

    private Long domainId;

    private String message;

    public static AlarmResponse toResponse(Long id, Long receiverUserId, AlarmType alarmType, DomainType domainType,
        Long domainId, String message) {
        return AlarmResponse.builder()
            .id(id)
            .receiverUserId(receiverUserId)
            .alarmType(alarmType)
            .domainType(domainType)
            .domainId(domainId)
            .message(message)
            .build();
    }

    @Builder
    private AlarmResponse(Long id, Long receiverUserId, AlarmType alarmType, DomainType domainType,
        Long domainId, String message) {
        this.id = id;
        this.receiverUserId = receiverUserId;
        this.alarmType = alarmType;
        this.domainType = domainType;
        this.domainId = domainId;
        this.message = message;
    }
}
