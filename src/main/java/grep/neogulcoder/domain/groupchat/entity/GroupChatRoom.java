package grep.neogulcoder.domain.groupchat.entity;

import grep.neogulcoder.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class GroupChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @JoinColumn(name = "study_id")
    private Long studyId;

    public GroupChatRoom(Long studyId) {
        this.studyId = studyId;
    }

    protected GroupChatRoom() {

    }
}
