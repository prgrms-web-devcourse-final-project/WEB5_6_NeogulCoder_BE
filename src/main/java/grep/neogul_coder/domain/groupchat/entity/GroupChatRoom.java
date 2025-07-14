package grep.neogul_coder.domain.groupchat.entity;

import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
public class GroupChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @JoinColumn(name = "study_id", nullable = false)
    private Long studyId;
}
