package grep.neogulcoder.domain.groupchat.entity;

import grep.neogulcoder.global.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Getter
public class GroupChatMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private GroupChatRoom groupChatRoom;

    @Column(name = "user_id")
    private Long userId;

    private String message;

    private LocalDateTime sentAt;

    public GroupChatMessage(GroupChatRoom room, Long userId, String message, LocalDateTime sentAt) {
        this.groupChatRoom = room;
        this.userId = userId;
        this.message = message;
        this.sentAt = sentAt;
    }

    protected GroupChatMessage() {

    }
}
