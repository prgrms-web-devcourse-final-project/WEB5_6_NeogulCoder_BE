package grep.neogulcoder.domain.alram.controller.dto.response;

import grep.neogulcoder.domain.alram.type.AlarmType;
import grep.neogulcoder.domain.alram.type.DomainType;
import java.time.LocalDateTime;
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

    private boolean checked;

    private LocalDateTime createdDate;

    public static AlarmResponse toResponse(Long id, Long receiverUserId, AlarmType alarmType,
        DomainType domainType,
        Long domainId, String message, boolean checked, LocalDateTime createdDate) {
        return AlarmResponse.builder()
            .id(id)
            .receiverUserId(receiverUserId)
            .alarmType(alarmType)
            .domainType(domainType)
            .domainId(domainId)
            .message(message)
            .checked(checked)
            .createdDate(createdDate)
            .build();
    }

    @Builder
    private AlarmResponse(Long id, Long receiverUserId, AlarmType alarmType, DomainType domainType,
        Long domainId, String message, boolean checked, LocalDateTime createdDate) {
        this.id = id;
        this.receiverUserId = receiverUserId;
        this.alarmType = alarmType;
        this.domainType = domainType;
        this.domainId = domainId;
        this.message = message;
        this.checked = checked;
        this.createdDate = createdDate;
    }
}
