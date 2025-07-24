package grep.neogulcoder.domain.study;

import grep.neogulcoder.domain.study.enums.StudyMemberRole;
import grep.neogulcoder.global.entity.BaseEntity;
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

    private boolean participated;

    protected StudyMember() {
    }

    @Builder
    public StudyMember(Study study, Long userId, StudyMemberRole role) {
        this.study = study;
        this.userId = userId;
        this.role = role;
        this.participated = false;
    }

    public static StudyMember createMember(Study study, Long userId) {
        return StudyMember.builder()
                .study(study)
                .userId(userId)
                .role(StudyMemberRole.MEMBER)
                .build();
    }

    public void delete() {
        this.activated = false;
    }

    public boolean isLeader() {
        return this.role == StudyMemberRole.LEADER;
    }

    public boolean hasNotRoleLeader() {
        return this.role != StudyMemberRole.LEADER;
    }

    public void changeRoleLeader() {
        this.role = StudyMemberRole.LEADER;
    }

    public void changeRoleMember() {
        this.role = StudyMemberRole.MEMBER;
    }

    public boolean isParticipated() {
        return this.participated;
    }

    public void participate() {
        this.participated = true;
    }
}
