package grep.neogul_coder.domain.groupchat.entity;

import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
public class GroupChatMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private GroupChatRoom groupChatRoom;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String content;

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public void setGroupChatRoom(GroupChatRoom groupChatRoom) {
        this.groupChatRoom = groupChatRoom;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
