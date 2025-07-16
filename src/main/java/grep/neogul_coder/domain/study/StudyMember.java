package grep.neogul_coder.domain.study;

import grep.neogul_coder.domain.study.enums.StudyMemberRole;
import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class StudyMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private StudyMemberRole role;

    protected StudyMember() {}

    @Builder
    public StudyMember(Study study, Long userId, StudyMemberRole role) {
        this.study = study;
        this.userId = userId;
        this.role = role;
    }

    public boolean hasNotRoleReader(){
        return this.role != StudyMemberRole.LEADER;
    }
}
